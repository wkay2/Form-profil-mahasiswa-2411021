package com.example.formmahasiswa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StudentProfileScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentProfileScreen() {
    // 1. STATE: Menyimpan nilai input form menggunakan mutableStateOf
    var nama by remember { mutableStateOf("Wahyu Krisadriyanto") }
    var nim by remember { mutableStateOf("2411021") }
    var prodi by remember { mutableStateOf("Informatika") }
    var noHp by remember { mutableStateOf("085705305304") }
    
    // State untuk beralih antara mode Edit dan mode Tampilan Kartu Profil
    var isEditing by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Form Profil Mahasiswa") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = if (isEditing) "Silakan Lengkapi Data Diri" else "Hasil Kartu Profil Mahasiswa",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )

                    if (isEditing) {
                        // EVENT: Memperbarui state ketika teks diubah
                        OutlinedTextField(
                            value = nama,
                            onValueChange = { nama = it },
                            label = { Text("Nama Lengkap") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = nim,
                            onValueChange = { nim = it },
                            label = { Text("NIM") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = prodi,
                            onValueChange = { prodi = it },
                            label = { Text("Program Studi") },
                            modifier = Modifier.fillMaxWidth()
                        )

                        OutlinedTextField(
                            value = noHp,
                            onValueChange = { noHp = it },
                            label = { Text("Nomor HP") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        // RECOMPOSITION: Bagian ini otomatis menampilkan data baru setelah tombol simpan diklik
                        ProfileItem(label = "Nama Lengkap", value = nama)
                        ProfileItem(label = "NIM", value = nim)
                        ProfileItem(label = "Program Studi", value = prodi)
                        ProfileItem(label = "Nomor HP", value = noHp)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // EVENT: Tombol untuk memicu perubahan state isEditing
                    Button(
                        onClick = { isEditing = !isEditing },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(if (isEditing) "Simpan & Tampilkan Profil" else "Edit Kembali")
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileItem(label: String, value: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        Text(text = value.ifEmpty { "-" }, style = MaterialTheme.typography.bodyLarge)
        HorizontalDivider(modifier = Modifier.padding(top = 4.dp), color = MaterialTheme.colorScheme.surfaceVariant)
    }
}
