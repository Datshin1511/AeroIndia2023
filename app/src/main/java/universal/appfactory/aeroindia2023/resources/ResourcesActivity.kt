package universal.appfactory.aeroindia2023.resources

import android.app.DownloadManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_resources.*
import universal.appfactory.aeroindia2023.BuildConfig
import universal.appfactory.aeroindia2023.R
import java.io.File
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class ResourcesActivity : AppCompatActivity() {

    private var download_file_link1 : String = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    private var download_file_link2 : String = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    private var download_file_link3 : String = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    private var download_file_link4 : String = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    private var download_file_link5 : String = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"
    private var download_file_link6 : String = "https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resources)

        download_file1.setOnClickListener {
            val fileName = download_file1.text.toString()
            openPdf(download_file_link1, fileName)
        }

        download_file2.setOnClickListener {
            val fileName = download_file2.text.toString()
            openPdf(download_file_link2, fileName)
        }

        download_file3.setOnClickListener {
            val fileName = download_file3.text.toString()
            openPdf(download_file_link3, fileName)
        }

        download_file4.setOnClickListener {
            val fileName = download_file4.text.toString()
            openPdf(download_file_link4, fileName)
        }

        download_file5.setOnClickListener {
            val fileName = download_file5.text.toString()
            openPdf(download_file_link5, fileName)
        }

        download_file6.setOnClickListener {
            val fileName = download_file6.text.toString()
            openPdf(download_file_link6, fileName)
        }
    }

    private fun openPdf(link: String, fileName: String){
        if(!ifFileExist(fileName)){
            initResources(link,fileName)
        }else{
            var file  = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName)
            var uri : Uri = FileProvider.getUriForFile(Objects.requireNonNull(application.applicationContext),
                BuildConfig.APPLICATION_ID +".provider",file)
            var i = Intent(Intent.ACTION_VIEW)
            i.setDataAndType(uri,"application/pdf")
            i.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(i)
        }

    }

    private fun initResources(link : String, fileName: String){
        var url : URL? = null
        try {
            url = URL(link)
        }catch (e : MalformedURLException){
            e.printStackTrace()
        }

        var request = DownloadManager.Request(Uri.parse(url.toString()))
        request.setTitle(fileName)
        request.setMimeType("application/pdf")
        request.allowScanningByMediaScanner()
        request.setAllowedOverMetered(true)
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,fileName)

        var downloadManager = getSystemService(AppCompatActivity.DOWNLOAD_SERVICE) as DownloadManager
        downloadManager.enqueue(request)
    }

    private fun ifFileExist(fileName : String): Boolean{
        var stringFileName : String = fileName.toString()
        var file : File = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString()+"/"+fileName)
        return file.exists()
    }

}