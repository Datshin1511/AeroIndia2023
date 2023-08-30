package universal.appfactory.aeroindia2023

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.*
import androidx.appcompat.app.ActionBar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Bearer token: 61b25a411a2dad66bb7b6ff145db3c2f

class UserRegistrationActivity : AppCompatActivity() {

    var navigableBundle: Bundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_registration)

        navigableBundle = intent.extras!!

        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        supportActionBar!!.setCustomView(R.layout.action_bar_layout)

        // Displays the Register activity
        setContentView(R.layout.activity_user_registration)
        val buttonId: Button = findViewById(R.id.registerButton)

        buttonId.setOnClickListener{
            val email = findViewById<EditText>(R.id.emailAddress).text.toString()
            val username = findViewById<EditText>(R.id.userName).text.toString()
            val mobileNo = findViewById<EditText>(R.id.mobileNumber).text.toString()
            val address = findViewById<EditText>(R.id.address).text.toString()
            val organisation = findViewById<EditText>(R.id.organisation).text.toString()
            val profileImage = "-"
            val designation = "-"

            Log.i("Unshared user information", "Name: $username\nMobile number: $mobileNo\nEmail: $email")

            navigableBundle.putString("email", email)
            navigableBundle.putString("username", username)
            navigableBundle.putString("mobileNo", mobileNo)
            navigableBundle.putString("address", address)
            navigableBundle.putString("organisation", organisation)
            navigableBundle.putString("designation", designation)
            navigableBundle.putString("profileImage", profileImage)

            if((email == "")||(username == "")||(mobileNo == "")){
                Toast.makeText(this@UserRegistrationActivity, "Fill the required columns", Toast.LENGTH_SHORT).show()
            }
            else {
                submitUserData(email, username, mobileNo)
            }
        }

    }

    @OptIn(DelicateCoroutinesApi::class)
    fun submitUserData(email: String, username: String, mobileNo: String) {
        val userDataRequestModel = UserDataRequestModel(username, mobileNo, email)

        //Accessing API Interface for pushing user data
        val response = ServiceBuilder.buildService(ApiInterface::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            response.sendUserData(userDataRequestModel,"Bearer 61b25a411a2dad66bb7b6ff145db3c2f").enqueue(
                object : Callback<UserRegisterResponseModel> {
                    override fun onResponse(
                        call: Call<UserRegisterResponseModel>,
                        response: Response<UserRegisterResponseModel>
                    ) {
                        val status = response.body()?.status.toString()
                        val userId = response.body()?.user_id.toString()
                        val verify = response.body()?.verify.toString()
                        val msg = response.body()?.message.toString()

                        //Warning in API response due to changing datatype of errors
                        val nameError = response.body()?.errors?.name.toString()
                        val phoneNoError = response.body()?.errors?.phone_no.toString()
                        val emailError  = response.body()?.errors?.email_id.toString()

                        if(status == "fail") {
                            Log.i("Components", "User ID: $userId, Verify: $verify, Status: $status, Msg: $msg")
                            if(phoneNoError != "0"){
                                Toast.makeText(this@UserRegistrationActivity, "Phone number exists", Toast.LENGTH_SHORT).show()
                                Log.i("Phone number error", phoneNoError)
                            }
                            else if(emailError != "0"){
                                Toast.makeText(this@UserRegistrationActivity, "Email exists", Toast.LENGTH_SHORT).show()
                                Log.i("Email error", emailError)
                            }
                            else if(nameError != "0") {
                                Toast.makeText(this@UserRegistrationActivity, "Name exists", Toast.LENGTH_SHORT).show()
                                Log.i("Name Error", nameError)
                            }
                            else{
                                Log.i("IMPORTANT", "No errors")
                            }
                        }
                        else{
                            // User registered successfully and pending for OTP verification
                            val intent = Intent(this@UserRegistrationActivity, OtpActivity::class.java)
                            navigableBundle.putString("userId", userId)
                            navigableBundle.putString("verify", verify)
                            intent.putExtras(navigableBundle)
                            startActivity(intent)
                            this@UserRegistrationActivity.finishAffinity()
                        }

                    }

                    override fun onFailure(call: Call<UserRegisterResponseModel>, text: Throwable ) {
                        Toast.makeText(this@UserRegistrationActivity, "Error", Toast.LENGTH_LONG)
                            .show()
                        Log.i("User registration failure", text.toString())
                    }
                }
            )
        }
    }
}