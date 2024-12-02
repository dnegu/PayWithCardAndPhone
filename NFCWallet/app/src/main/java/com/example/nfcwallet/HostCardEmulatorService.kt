package com.example.nfcwallet

import android.content.Intent
import android.nfc.cardemulation.HostApduService
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.KITKAT)
class HostCardEmulatorService: HostApduService() {
    override fun onDeactivated(reason: Int) = Unit
    private var inputData: String? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent != null && intent.hasExtra("input_data")) {
            inputData = intent.getStringExtra("input_data")
            println(inputData)
        }
        return super.onStartCommand(intent, flags, startId)
    }
    @OptIn(ExperimentalStdlibApi::class)
    override fun processCommandApdu(commandApdu: ByteArray?,
                                    extras: Bundle?): ByteArray {
        if (commandApdu == null) {
            return ("STATUS_FAILED").toByteArray()
        }
        val hexCommandApdu = commandApdu.toHexString()
        println(hexCommandApdu)
        val response = inputData
        return if (response != null){
            val i = Intent(this@HostCardEmulatorService, PaymentSuccessActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(i)
            response.toByteArray()
        }else{
            "No input data received".toByteArray()
        }
    }
}