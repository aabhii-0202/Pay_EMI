package com.mediustechnologies.payemi.activities

import android.Manifest
import android.content.Context
import com.mediustechnologies.payemi.helper.BaseAppCompatActivity
import android.os.Bundle
import com.mediustechnologies.payemi.ApiResponse.TransactionDetails
import com.mediustechnologies.payemi.R
import com.bumptech.glide.Glide
import android.content.Intent
import com.mediustechnologies.payemi.activities.DashBoardclasses.Home_Nav
import android.os.Build
import androidx.core.content.FileProvider
import android.content.pm.ResolveInfo
import android.content.pm.PackageManager
import android.widget.Toast
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat
import com.mediustechnologies.payemi.databinding.ActivityTransactionDetailsBinding
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

class Transaction_Details : BaseAppCompatActivity() {
    private var binding: ActivityTransactionDetailsBinding? = null
    private val context: Context = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTransactionDetailsBinding.inflate(
            layoutInflater
        )
        setContentView(binding!!.root)
        init()
    }

    private fun formatdate(date: String): String {
        var ans = ""
        val day = date.substring(8, 10)
        ans += day + ""
        val month = date.substring(5, 7)
        val months = arrayOf(
            "Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep",
            "Oct", "Nov", "Dec"
        )
        val m = month.toInt()
        try {
            ans += months[m - 1]
        } catch (e: Exception) {
            ans = "Error finding month"
        }
        ans += " " + date.substring(2, 4)
        val time = date.substring(11, 16)
        val hr = time.substring(0, 2)
        val min = time.substring(3)
        val ampm: String
        var hour = hr.toInt()
        ampm = if (hour > 12) {
            "PM"
        } else "AM"
        hour = hour % 12
        ans = "$ans  $hour:$min $ampm"
        return ans
    }

    private fun init() {
        val item: TransactionDetails? = intent.getParcelableExtra("item")
        println("Transaction history item: $item")
        try {
            if (item!!.type == "transaction") {
                binding!!.transactionId.text = item!!.razorpay_transaction_id
                binding!!.detailsbankname.text = "To - " + item!!.biller_name
                binding!!.customerName.text = "From - " + item!!.customer_name
                binding!!.customeremail.text = "" + item!!.customer_email
                binding!!.billerid.text = "Bill Id - " + item!!.bill_id
                binding!!.tobankname.text = "To " + item!!.biller_name
                binding!!.paidAmount.text = item!!.amount
                val status = item.bbps_transaction_status
                var date = item.transaction_datetime
                date = formatdate(date)
                try {
                    if (status.equals("Successful", ignoreCase = true) ||
                        status.equals("success", ignoreCase = true) ||
                        status.equals("su", ignoreCase = true)
                    ) {
                        binding!!.statusdate.text = "  Completed | $date"
                        binding!!.statusdate.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_tick,
                            0,
                            0,
                            0
                        )
                    } else if (status.equals("failed", ignoreCase = true) ||
                        status.equals("fa", ignoreCase = true)
                    ) {
                        binding!!.statusdate.text = "  Failed | $date"
                        binding!!.statusdate.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_cross,
                            0,
                            0,
                            0
                        )
                    } else {
                        binding!!.statusdate.text = "  Pending | $date"
                        binding!!.statusdate.setCompoundDrawablesWithIntrinsicBounds(
                            R.drawable.ic_processing,
                            0,
                            0,
                            0
                        )
                    }
                } catch (e: Exception) {
                    binding!!.statusdate.text = "  Pending | $date"
                    binding!!.statusdate.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.ic_processing,
                        0,
                        0,
                        0
                    )
                }
                val url = intent.getStringExtra("logo")
                Glide.with(binding!!.image).load(url).into(binding!!.image)
            }
        } catch (e: Exception) { }
        binding!!.backButton.setOnClickListener { view: View? -> finish() }
        binding!!.sharebutton.setOnClickListener { view: View? ->
            val file = save()
            file?.let { share(it) }
        }
        binding!!.havingIssue.setOnClickListener { view: View? ->
            val i = Intent(context, Home_Nav::class.java)
            i.putExtra("fragment", Home_Nav.POS_COMPLAINT_REG)
            i.putExtra("transactionId", item!!.razorpay_transaction_id)
            //todo for above line in complaint fragment
            startActivity(i)
        }
    }

    private fun share(file: File) {
        val uri: Uri
        uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            FileProvider.getUriForFile(context, "$packageName.provider", file)
        } else {
            Uri.fromFile(file)
        }
        val intent = Intent()
        intent.action = Intent.ACTION_SEND
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_SUBJECT, "Screenshot")
        intent.putExtra(Intent.EXTRA_TEXT, resources.getString(R.string.Share_Message))
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        val chooser = Intent.createChooser(intent, "Share Payment Image")
        try {
            val resInfoList = this.packageManager.queryIntentActivities(
                chooser,
                PackageManager.MATCH_DEFAULT_ONLY
            )
            for (resolveInfo in resInfoList) {
                val packageName = resolveInfo.activityInfo.packageName
                grantUriPermission(
                    packageName,
                    uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION
                )
            }
            startActivity(chooser)
        } catch (e: Exception) {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
        }
    }

    private fun save(): File? {
        if (!checkPermission()) {
            return null
        }
        try {
//            String path = Environment.getExternalStorageDirectory().toString()+"/PayEMI";
            val path = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS + "/.PayEMI").toString()
            val fileDir = File(path)
            if (!fileDir.exists()) {
                fileDir.mkdir()
            }
            val mpath = path + "PayEmi" + ".png"
            //            mpath.replaceAll(":", ".");
            val bitmap = screenshot()
            val file = File(mpath)
            val fout = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fout)
            fout.flush()
            fout.close()
            Toast.makeText(context, "Image saved successfully.", Toast.LENGTH_LONG).show()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("tag", "While taking screen shot $e")
        }
        return null
    }

    private fun screenshot(): Bitmap {
        val v = findViewById<View>(R.id.linearLayout2)
        val bitmap = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        v.draw(canvas)
        return bitmap
    }

    private fun checkPermission(): Boolean {
        val permission =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val permission2 =
            ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED || permission2 != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ),
                1
            )
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            val file = save()
            file?.let { share(it) }
        } else Toast.makeText(context, "Grant permission to share image.", Toast.LENGTH_SHORT)
            .show()
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}