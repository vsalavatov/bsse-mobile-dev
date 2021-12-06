package com.vadimsalavatov.mobiledev

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment {
    constructor(): super()
    constructor(@LayoutRes contentLayoutId: Int): super(contentLayoutId)
}