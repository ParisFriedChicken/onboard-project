
#Architecture notes

All write operations are executed within short-lived transactions at `READ COMMITTED` level. Optimistic locking (`@Version`) ensures data integrity on concurrent updates without blocking. The **game** table is probably going to be updated by multiple users at the same time, that's the reason of the optimistic locking on it.
