package com.tellirium.farmer.ui.fragments

import com.tellirium.farmer.db.model.User

sealed class LoginContract {
    // Contract class to display progress bar
    class ProgressDisplay(val display: Boolean) : LoginContract()

    // Contract class to display message to the user
    object ReadFailed : LoginContract()
}

sealed class FarmerListContract {
    // Contract class to display progress bar
    class ProgressDisplay(val display: Boolean) : FarmerListContract()

    // Contract class to display message to the user
    object ReadFailed : FarmerListContract()
}