package com.example.bootscounter.main

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bootscounter.R
import com.example.bootscounter.databinding.ActivityMainBinding
import com.example.bootscounter.receiver.BootBroadcastReceiver
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var receiver: BootBroadcastReceiver
    private val viewModel: MainViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerBootReceiver()

        bind()
    }

    private fun registerBootReceiver() {
        receiver = BootBroadcastReceiver()
        IntentFilter(Intent.ACTION_BOOT_COMPLETED).also {
            registerReceiver(receiver, it)
        }
    }

    private fun bind() {
        viewModel.bootList.observe(this) {
            val bootInfo = when (it.size) {
                0 -> getString(R.string.no_boots_detected)
                else -> {
                    var info = ""
                    it.forEachIndexed { index, boot ->
                        info += "${index + 1}. ${boot.timestamp}\n"
                    }
                    info
                }
            }
            binding.bootInfoTextView.text = bootInfo
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.checkBootList()
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }

}