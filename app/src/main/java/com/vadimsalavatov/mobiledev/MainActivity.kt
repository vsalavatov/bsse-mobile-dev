package com.vadimsalavatov.mobiledev

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.vadimsalavatov.mobiledev.databinding.ActivityMainBinding
import by.kirich1409.viewbindingdelegate.viewBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val viewModel: MainViewModel by viewModels()

    private val viewBinding by viewBinding(ActivityMainBinding::bind)
}