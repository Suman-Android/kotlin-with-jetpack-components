package com.amadeus.myapplication.utils

import androidx.databinding.ViewDataBinding

/**
 * Created by Suman Singh on 6/7/2022.
 */


fun <T : ViewDataBinding> T.executeWithAction(action: T.() -> Unit) {
    action()
    executePendingBindings()
}