package tr.com.mermela.cybersecurityfinalproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import tr.com.mermela.cybersecurityfinalproject.R
import tr.com.mermela.cybersecurityfinalproject.databinding.ActivityMainBinding
import tr.com.mermela.cybersecurityfinalproject.ui.devicelist.DeviceListFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = DeviceListFragment()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer,fragment)
            .addToBackStack(null)
            .commit()

    }
}