package io.pc7.ninu.domain.usecase

import io.pc7.ninu.data.network.repository.GeneralRepository
import io.pc7.ninu.database.Dao
import io.pc7.ninu.database.model.Device

class SaveDeviceToDatabase(
    private val dao: Dao,
    private val generalRepository: GeneralRepository,

){
    suspend operator fun invoke(
        macAddress: String
    ){
        dao.insert(Device(macAddress = macAddress))//TODO check if already exist on my device
        //generalRepository. TODO send device data if needed

    }

}