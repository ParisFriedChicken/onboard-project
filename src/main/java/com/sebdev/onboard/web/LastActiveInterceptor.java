package com.sebdev.onboard.web;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;
import com.sebdev.onboard.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LastActiveInterceptor implements HandlerInterceptor {

    private final PlayerRepository playerRepository;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    // Throttle updates to avoid a DB write on every single request (default: 1 minute)
    private static final long THROTTLE_MILLIS = 60_000L;

    // Backwards-compatible constructor used by unit tests and any manual instantiation
    public LastActiveInterceptor(PlayerRepository playerRepository) {
        this(playerRepository, null, null);
    }

    @Autowired
    public LastActiveInterceptor(PlayerRepository playerRepository, JwtService jwtService, UserDetailsService userDetailsService) {
        this.playerRepository = playerRepository;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            // Try to recover username from Authorization header if JwtService is available
            // (handled later)
        }

        Object principal = authentication != null ? authentication.getPrincipal() : null;
        String username = null;
        if (principal instanceof org.springframework.security.core.userdetails.UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else if (principal instanceof String) {
            username = (String) principal;
        }

        // If we don't have a username from the security context, attempt to extract it from
        // a Bearer JWT in the Authorization header when the context is anonymous or unauthenticated.
        if ((username == null || username.isBlank()) && (authentication == null || authentication instanceof AnonymousAuthenticationToken || !authentication.isAuthenticated())) {
            try {
                final String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ") && jwtService != null) {
                    final String jwt = authHeader.substring(7);
                    final String userEmail = jwtService.extractUsername(jwt);
                    if (userEmail != null && !userEmail.isBlank()) {
                        // If we can validate the token against user details, do so; otherwise accept the extracted subject.
                        if (userDetailsService != null) {
                            try {
                                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                                if (jwtService.isTokenValid(jwt, userDetails)) {
                                    username = userEmail;
                                }
                            } catch (Exception ex) {
                                // If validation/loading fails, don't set username.
                                // Fail quietly: do not interrupt request processing.
                            }
                        } else {
                            username = userEmail;
                        }
                    }
                }
            } catch (Exception ex) {
                // Ignore problems extracting username from JWT; we don't want to fail the request.
            }
        }

        if (username == null || username.isBlank()) {
            return true;
        }

        try {
            Optional<Player> maybePlayer = playerRepository.findByEmail(username);
            if (maybePlayer.isPresent()) {
                Player player = maybePlayer.get();
                long now = System.currentTimeMillis();
                Date last = player.getLastActiveAt();
                if (last == null || (now - last.getTime()) >= THROTTLE_MILLIS) {
                    player.setLastActiveAt(new Date(now));
                    playerRepository.save(player);
                }
            }
        } catch (Exception ex) {
            // Don't fail the request if updating last active fails; just log to stderr for now.
            System.err.println("Failed to update last_active_at for user '" + username + "': " + ex.getMessage());
        }

        return true;
    }
}