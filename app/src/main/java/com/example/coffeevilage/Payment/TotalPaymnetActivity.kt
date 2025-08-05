package com.example.coffeevilage.Payment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeevilage.MainActivity
import com.example.coffeevilage.R
import kr.co.bootpay.android.Bootpay
import kr.co.bootpay.android.constants.BootpayBuildConfig
import kr.co.bootpay.android.events.BootpayEventListener
import kr.co.bootpay.android.models.BootExtra
import kr.co.bootpay.android.models.BootItem
import kr.co.bootpay.android.models.BootUser
import kr.co.bootpay.android.models.Payload
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TotalPaymentActivity : AppCompatActivity() {
    var applicationId = "688c70ae86cd66f61255b677"
    private var price = 0.0
    private var orderId = ""
    private var orderName = ""

    private var isCart = false //주문 경로 처리를 위한 변수
    private var orderCnt = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BootpayBuildConfig.DEBUG) {
            applicationId = "688c70ae86cd66f61255b677"
        }
        orderId = generateOrderId()
        price = intent.getDoubleExtra("price", 0.0)
        orderName = intent.getStringExtra("orderName") ?: ""
        isCart = intent.getBooleanExtra("cart", false)
        orderCnt = intent.getIntExtra("orderCnt",0)
        PaymentTest()
    }


    fun PaymentTest() {
        val extra = BootExtra()
            .setCardQuota("0,2,3") // 일시불, 2개월, 3개월 할부 허용, 할부는 최대 12개월까지 사용됨 (5만원 이상 구매시 할부허용 범위)
        /*   val items: MutableList<BootItem> = ArrayList()
           val item1 = BootItem().setName("마우's 스").setId("ITEM_CODE_MOUSE").setQty(1).setPrice(500.0)
           val item2 = BootItem().setName("키보드").setId("ITEM_KEYBOARD_MOUSE").setQty(1).setPrice(500.0)
           items.add(item1)
           items.add(item2)*/
        val payload = Payload()

        payload.setApplicationId(applicationId)
            .setOrderName(orderName)
            .setOrderId(orderId)
            .setPrice(price)
            .setUser(getBootUser())
        //  .setPg("페이레터")
        //  .setMethod("카드자동")
        //   .setExtra(extra).items = items
        Log.d("payload", "${payload}")

        val map: MutableMap<String, Any> = HashMap()
        map["1"] = "abcdef"
        map["2"] = "abcdef55"
        map["3"] = 1234
        payload.metadata = map
        //        payload.setMetadata(new Gson().toJson(map));
        Bootpay.init(supportFragmentManager, applicationContext)
            .setPayload(payload)
            .setEventListener(object : BootpayEventListener {
                override fun onCancel(data: String) {
                    Log.d("bootpay--", "cancel: $data")
                }

                override fun onError(data: String) {
                    Log.d("bootpay--", "error: $data")
                }

                override fun onClose() {
                    Log.d("bootpay--", "cancel---")
                    //임의로 결제 완료 됐다는 로직을 위함임.
                    val resultIntent = Intent().apply {
                        putExtra("paymentResult", true)
                        putExtra("orderCnt", orderCnt)
                        putExtra("orderMsg", orderName)
                        if (isCart) {
                            putExtra("isCart", true)
                        }
                    }
                    setResult(Activity.RESULT_OK, resultIntent)
                    Bootpay.removePaymentWindow()
                    finish()

                }

                override fun onIssued(data: String) {
                    Log.d("bootpay--", "issued: $data")
                }

                override fun onConfirm(data: String): Boolean {
                    Log.d("bootpay--", "confirm: $data")
                    //                        Bootpay.transactionConfirm(data); //재고가 있어서 결제를 진행하려 할때 true (방법 1)
                    return true //재고가 있어서 결제를 진행하려 할때 true (방법 2)
                    //                        return false; //결제를 진행하지 않을때 false
                }

                override fun onDone(data: String) {
                    Log.d("done--", data)
                }
            }).requestPayment()
    }

    fun getBootUser(): BootUser? {
        val userId = "123411aaaaaaaaaaaabd4ss121"
        val user = BootUser()
        user.id = userId
        user.area = "서울"
        user.gender = 1 //1: 남자, 0: 여자
        user.email = "test1234@gmail.com"
        user.phone = "010-1234-4567"
        user.birth = "1988-06-10"
        user.username = "홍길동"
        return user
    }

    //결제를 요청하는 주문단위의 고유 식별자 --> 추후 서버를 연동하고 있다면 서버에서 생성해서 전달해주는 방식으로 변경해야함.
    fun generateOrderId(): String {
        val timestamp = SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(Date())
        return "ORD$timestamp"
    }


}


