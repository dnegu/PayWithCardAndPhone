package com.example.nfcreader

import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.NfcA
import android.nfc.tech.NfcB
import android.nfc.tech.IsoDep
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.nfcreader.databinding.ActivityPaymentBinding
import com.github.devnied.emvnfccard.parser.EmvTemplate
import kotlinx.coroutines.runBlocking
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST
import java.io.IOException
import java.time.LocalDate
import java.time.ZoneId


@RequiresApi(Build.VERSION_CODES.KITKAT)
class PaymentActivity : AppCompatActivity(), NfcAdapter.ReaderCallback{
    private lateinit var nfcAdapter: NfcAdapter
    companion object {
        private var BACKEND_URL = "https://stripe-node-lik1.onrender.com"
    }
    data class PaymentRequest(val customerId: String, val paymentMethodId: String, val paymentAmount: Int)
    data class PaymentCardRequest(val cardNumber: String, val paymentAmount: Int)
    interface ApiService {
        @POST("/pay")
        suspend fun pay(@Body paymentRequest: PaymentRequest): PaymentMethodResponse

        @POST("/pay-with-card")
        suspend fun payCard(@Body paymentCardRequest: PaymentCardRequest): PaymentMethodResponse
    }
    private suspend fun sendPayRequest(customerId: String, pmId: String,paymentAmount: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        try {
            val response = apiService.pay(PaymentRequest(customerId, pmId, paymentAmount))
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()
            }
            println(response)
        } catch (e: Exception) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "Payment not successful", Toast.LENGTH_SHORT).show()
            }
            println("Error occurred: ${e.message}")
        }
        val i = Intent(this@PaymentActivity, MainActivity::class.java)
        startActivity(i)
    }
    private suspend fun sendPayCardRequest(cardNumber: String, paymentAmount: Int) {
        val retrofit = Retrofit.Builder()
            .baseUrl(BACKEND_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)
        try {
            val response = apiService.payCard(PaymentCardRequest(cardNumber, paymentAmount))
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show()
            }
            println(response)
        } catch (e: Exception) {
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(this, "Payment not successful", Toast.LENGTH_SHORT).show()
            }
            println("Error occurred: ${e.message}")
        }
        val i = Intent(this@PaymentActivity, MainActivity::class.java)
        startActivity(i)
    }
    private lateinit var binding: ActivityPaymentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available on this device", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        if (!nfcAdapter!!.isEnabled) {
            Toast.makeText(this, "Please enable NFC", Toast.LENGTH_SHORT).show()
        }
    }
    public override fun onResume() {
        super.onResume()
        val options = Bundle()
        options.putInt(NfcAdapter.EXTRA_READER_PRESENCE_CHECK_DELAY, 250)
        nfcAdapter?.enableReaderMode(this, this,
            NfcAdapter.FLAG_READER_NFC_A or
                    NfcAdapter.FLAG_READER_NFC_B or
                    NfcAdapter.FLAG_READER_NFC_F or
                    NfcAdapter.FLAG_READER_NFC_V or
                    NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK,
            options)
    }
    public override fun onPause() {
        super.onPause()
        nfcAdapter?.disableReaderMode(this)
    }
    private fun isWallet(custPmIdArr: List<String>?):Boolean = custPmIdArr?.size == 2

    override fun onTagDiscovered(tag: Tag?) {
        try{
            val isoDep = IsoDep.get(tag)
            isoDep.connect()
            isoDep.timeout = 5000
            val response = isoDep.transceive(Utils.hexStringToByteArray(
                "00A4040007A0000002471001"))
            var responseStr = response.toString(Charsets.UTF_8)
            var custPmIdArr = responseStr.split("+")
            var paymentAmount = intent.getStringExtra("paymentAmount")
            println(custPmIdArr)
            runBlocking {
                try {
                    if(paymentAmount != null){
                        if(isWallet(custPmIdArr))
                            sendPayRequest(custPmIdArr[0], custPmIdArr[1],paymentAmount.toInt())
                        else{
                            val provider = PcscProvider()
                            provider.setmTagCom(isoDep)

                            val config = EmvTemplate.Config()
                                .setContactLess(true)
                                .setReadAllAids(true)
                                .setReadTransactions(true)
                                .setRemoveDefaultParsers(false)
                                .setReadAt(true)
                            val parser = EmvTemplate.Builder()
                                .setProvider(provider)
                                .setConfig(config)
                                .build()
                            val card = parser.readEmvCard()
                            val cardNumber = card.cardNumber
                            sendPayCardRequest(cardNumber, paymentAmount.toInt())
                        }
                    }
                }catch(e:Exception){
                    println(e)
                }
            }
            isoDep.close()
            val i = Intent(this@PaymentActivity,PaymentSuccessActivity::class.java)
            i.putExtra("paymentAmount",paymentAmount)
            startActivity(i)
        }catch(e:Exception){
            println(e.message)
        }
    }
}
