package universal.appfactory.aeroindia2023

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_profile_info.*
import kotlinx.android.synthetic.main.activity_user_login.*
import kotlinx.android.synthetic.main.zonal_manager_user_card.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Bearer token: 61b25a411a2dad66bb7b6ff145db3c2f

class UserLoginActivity : AppCompatActivity() {

    var backpress: Int = 0
    var navigableBundle = Bundle()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        supportActionBar!!.setCustomView(R.layout.action_bar_layout)

        val loginButtonId = findViewById<Button>(R.id.loginButton)
        val signUpButtonId = findViewById<Button>(R.id.signUpButton)
        val emailEditViewId = findViewById<EditText>(R.id.emailAddress)

        var intent: Intent

        val sharedPreferences: SharedPreferences = getSharedPreferences("LocalUserData", MODE_PRIVATE)

        // True condition needs to be released
        if(sharedPreferences.getBoolean("loginStatus", false) || true) {

            navigableBundle.putString("name", sharedPreferences.getString("name", "Default name"))
            navigableBundle.putString("email", sharedPreferences.getString("email", "defaultemail@dummy.com"))
            navigableBundle.putString("phoneNo", sharedPreferences.getString("phoneNo", "1234567890"))
            navigableBundle.putString("designation", sharedPreferences.getString("designation", "NA"))
            navigableBundle.putString("userId", sharedPreferences.getString("userId", "1"))
            navigableBundle.putString("foreignKeyId", sharedPreferences.getString("foreignKeyId", "1"))
            navigableBundle.putString("userType", sharedPreferences.getString("userType", "-"))
            navigableBundle.putString("organisation", sharedPreferences.getString("organisation", "1"))
            navigableBundle.putString("profileImage", sharedPreferences.getString("profileImage", "-"))
            navigableBundle.putString("address", sharedPreferences.getString("address", "Dummy address"))

            backpress=0
            intent = Intent(this@UserLoginActivity, HomepageActivity::class.java)
            intent.putExtras(navigableBundle)
            startActivity(intent)
            this@UserLoginActivity.finishAffinity()
        }

        else {
            loginButtonId.setOnClickListener {
                val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(loginButtonId.windowToken, 0)

                val email = emailEditViewId.text.toString()
                if(email != "") {
                    Log.i("Email", email)
                    navigableBundle.putString("type", "1")
                    submitUserLoginData(email)
                }
                else{
                    Toast.makeText(this@UserLoginActivity, "Fill all columns", Toast.LENGTH_SHORT).show()
                }
            }

            signUpButtonId.setOnClickListener {
                backpress=0
                intent = Intent(this@UserLoginActivity, UserRegistrationActivity::class.java)
                navigableBundle.putString("type", "2")
                intent.putExtras(navigableBundle)
                startActivity(intent)
            }
        }

    }

    private fun submitUserLoginData(email: String){
        val userLoginDataRequestModel = UserLoginDataRequestModel(email)

        //Accessing API Interface for verifying user email ID
        val response = ServiceBuilder.buildService(ApiInterface::class.java)

        @OptIn(DelicateCoroutinesApi::class)
        GlobalScope.launch(Dispatchers.IO) {
            response.verifyUserLogin(userLoginDataRequestModel,"Bearer 61b25a411a2dad66bb7b6ff145db3c2f").enqueue(
                object : Callback<UserLoginDataResponseModel> {
                    override fun onResponse(
                        call: Call<UserLoginDataResponseModel>,
                        response: Response<UserLoginDataResponseModel>
                    ) {
                        // User data
                        val status = response.body()?.status.toString()
                        val userId = response.body()?.message?.user_id.toString()
                        val verificationCode = response.body()?.message?.verification_code.toString()

                        // Login errors
                        val emailError = response.body()?.errors?.email_id.toString()

                        Log.i("Verification code", verificationCode)

                        if(status == "fail"){
                            Toast.makeText(this@UserLoginActivity, "Status: $status", Toast.LENGTH_SHORT).show()
                            Log.i("Login Activity email error", emailError)
                            if(emailError.subSequence(0, 7) == "Account"){
                                MaterialAlertDialogBuilder(this@UserLoginActivity)
                                    .setTitle("ALERT !")
                                    .setMessage("Your $emailError due to multiple incorrect login attempts. Kindly try after the locking time.")
                                    .setNeutralButton("OK") { _, _ ->
                                        this@UserLoginActivity.finishAffinity()
                                    }
                                    .show()
                            }
                        }
                        else{
                            navigableBundle.putString("email", email)
                            navigableBundle.putString("userId", userId)

                            backpress=0
                            val navigateIntent = Intent(this@UserLoginActivity, OtpActivity::class.java)
                            navigateIntent.putExtras(navigableBundle)
                            startActivity(navigateIntent)
                            this@UserLoginActivity.finish()
                        }

                    }

                    override fun onFailure(call: Call<UserLoginDataResponseModel>, text: Throwable) {
                        Toast.makeText(this@UserLoginActivity, "Error", Toast.LENGTH_LONG)
                            .show()
                        Log.i("User Login failure", text.toString())
                    }
                }
            )
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed(){
        backpress += 1
        if(backpress > 1){
            finishAffinity()
        }
        else{
            Toast.makeText(this, "Press back once again to exit", Toast.LENGTH_SHORT).show()
        }
    }

}
