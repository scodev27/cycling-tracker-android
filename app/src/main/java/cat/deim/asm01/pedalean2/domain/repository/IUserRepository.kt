package cat.deim.asm01.pedalean2.domain.repository

import cat.deim.asm01.pedalean2.domain.models.User

interface IUserRepository {
    /**
     * User Repository Interface
     * Defines the methods for managing users in the application.
     */
    /**
     * Retrieves a user by their unique identifier (UUID).
     *
     * @param uuid The unique identifier of the user to retrieve.
     * @return The user associated with the provided UUID.
     */
    fun getUserById(uuid: String): User

    /**
     * Retrieves the currently active user in the application.
     *
     * @return The active user.
     */
    fun getActiveUser(): User
    /**
     * Sets the specified user as the active user in the application.
     *
     * @param user The user to set as active.
     */
    fun setActiveUser(user: User)

    /**
     * Updates the information of the currently active user in the application.
     *
     * @param user The user with updated information to be set as active.
     */
    fun updateActiveUser(user: User)

    /**
        * Deletes the currently active user from the application.
        * @param user The user is used to verify that the user to be deleted is the active user.
        */
    fun deleteActiveUser(user: User)

}