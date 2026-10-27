package tech.mohamedashraf.data.repository

import tech.mohamedashraf.data.firebase.Authentication
import tech.mohamedashraf.data.firebase.FireStoreDB
import tech.mohamedashraf.domain.repository.MarketplaceRepository

class MarketplaceRepositoryImplementation(private val authentication: Authentication, private val fireStoreDB: FireStoreDB) : MarketplaceRepository {

}