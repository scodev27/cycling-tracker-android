package cat.deim.asmxx.pedalean2.domain.repository

import cat.deim.asmxx.pedalean2.domain.models.Rent

interface IRentRepository {
    /** Returns a list of all rents. */
    fun getAllRents(): List<Rent>
    /** Creates the given rents. Returns the number of rents created.*/
    fun createAllRents(rents: List<Rent>): Int
    /** Updates the given rents. Returns the number of rents updated.*/
    fun updateAllRents(rents: List<Rent>): Int
    /** Deletes all rents. Returns the number of rents deleted.*/
    fun deleteAllRents(): Int

    /** Returns the rent with the given uuid, or null if it doesn't exist.*/
    fun getRentByUuid(uuid: String): Rent?
    /** Creates the given rent. Returns true if the rent was created, false otherwise.*/
    fun createRent(rent: Rent): Boolean

    /** Updates the rent with the same uuid as the given rent. Returns true if the rent was updated, false otherwise.*/
    fun updateRent(rent: Rent): Boolean

    /** Deletes the rent with the given uuid. Returns true if the rent was deleted, false otherwise.*/
    fun deleteRent(uuid: String): Boolean

    /** Returns true if the rent with the given uuid is active, false otherwise.*/
    fun isRentActive(uuid: String): Boolean
}