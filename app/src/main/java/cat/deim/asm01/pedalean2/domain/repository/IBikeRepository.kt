package cat.deim.asmxx.pedalean2.domain.repository

import cat.deim.asmxx.pedalean2.domain.models.Bike

interface IBikeRepository {
    /**
     * Inserts a list of bikes into the repository.
     * @param bikes The list of bikes to insert.
     * @return The number of bikes successfully inserted.
     */
    fun insertAll(bikes: List<Bike>): Int

    /**
     * Retrieves all bikes from the repository.
     * @return A list of all bikes in the repository.
     */
    fun getAll(): List<Bike>

    /**
     * Updates a list of bikes in the repository.
     * @param bikes The list of bikes to update.
     * @return The number of bikes successfully updated.
     */
    fun updateAll(bikes: List<Bike>): Int

    /**
     * Deletes all bikes from the repository.
     * @return The number of bikes successfully deleted.
     */
    fun deleteAll(): Int

    /**
     * Inserts a single bike into the repository.
     * @param bike The bike to insert.
     * @return True if the bike was successfully inserted, false otherwise.
     */
    fun insert(bike: Bike): Boolean

    /**
     * Retrieves a bike by its unique identifier (UUID).
     * @param uuid The unique identifier of the bike to retrieve.
     * @return The bike associated with the provided UUID, or null if not found.
     */
    fun getByUuid(uuid: String): Bike?

    /**
     * Updates a single bike in the repository.
     * @param bike The bike to update.
     * @return True if the bike was successfully updated, false otherwise.
     */
    fun update(bike: Bike): Boolean

    /**
     * Deletes a bike by its unique identifier (UUID).
     * @param uuid The unique identifier of the bike to delete.
     * @return True if the bike was successfully deleted, false otherwise.
     */
    fun delete(uuid: String): Boolean
}