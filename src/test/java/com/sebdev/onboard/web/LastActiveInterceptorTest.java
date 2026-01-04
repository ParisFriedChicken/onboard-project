package com.sebdev.onboard.web;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.sebdev.onboard.model.Player;
import com.sebdev.onboard.repository.PlayerRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class LastActiveInterceptorTest {

    private PlayerRepository playerRepository;
    private LastActiveInterceptor interceptor;

    private SecurityContext originalContext;

    @BeforeEach
    void setUp() {
        playerRepository = Mockito.mock(PlayerRepository.class);
        interceptor = new LastActiveInterceptor(playerRepository);
        // preserve original security context
        originalContext = SecurityContextHolder.getContext();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.setContext(originalContext);
    }

    @Test
    void updatesWhenStale() throws Exception {
        String email = "test@example.com";
        long now = System.currentTimeMillis();
        // older than throttle (use 61 seconds)
        Date last = new Date(now - 61_000L);
        Player player = new Player();
        player.setEmail(email);
        player.setLastActiveAt(last);

        when(playerRepository.findByEmail(email)).thenReturn(Optional.of(player));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(email, "N/A"));
        SecurityContextHolder.setContext(context);

        interceptor.preHandle(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class), new Object());

        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    void doesNotUpdateWhenRecent() throws Exception {
        String email = "recent@example.com";
        long now = System.currentTimeMillis();
        // recent (use now)
        Date last = new Date(now);
        Player player = new Player();
        player.setEmail(email);
        player.setLastActiveAt(last);

        when(playerRepository.findByEmail(email)).thenReturn(Optional.of(player));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(new UsernamePasswordAuthenticationToken(email, "N/A"));
        SecurityContextHolder.setContext(context);

        interceptor.preHandle(Mockito.mock(HttpServletRequest.class), Mockito.mock(HttpServletResponse.class), new Object());

        verify(playerRepository, never()).save(any(Player.class));
    }
}
