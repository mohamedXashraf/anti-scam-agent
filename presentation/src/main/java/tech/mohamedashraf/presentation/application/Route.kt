package tech.mohamedashraf.presentation.application

import kotlinx.serialization.Serializable

@Serializable
sealed class Route {

    @Serializable
    data object SPLASH : Route()

    @Serializable
    data object HOME : Route()

    @Serializable
    data object ITEM_DETAILS : Route()


    // Complex Routes

    @Serializable
    data object BACK : Route()

    @Serializable
    data object BASE_SPLASH : Route()

    @Serializable
    data object BASE_HOME : Route()

    @Serializable
    data object RESET_HOME : Route()
}
