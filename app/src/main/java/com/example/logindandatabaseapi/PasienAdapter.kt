package com.example.logindandatabaseapi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.logindandatabaseapi.model.Pasien

class PasienAdapter(private val listPasien: List<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>() {

    class PasienViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNama: TextView = view.findViewById(R.id.tvNamaPasien)
        val tvDetail: TextView = view.findViewById(R.id.tvDetailPasien)
        val tvAlamat: TextView = view.findViewById(R.id.tvAlamatPasien)
        val tvNoTelp: TextView = view.findViewById(R.id.tvNoTelp)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pasien, parent, false)
        return PasienViewHolder(view)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien = listPasien[position]
        holder.tvNama.text = pasien.nama
        holder.tvDetail.text = "${pasien.tanggal_lahir} | ${if (pasien.jenis_kelamin == "L") "Laki-laki" else "Perempuan"}"
        holder.tvAlamat.text = pasien.alamat
        holder.tvNoTelp.text = pasien.no_telepon
    }

    override fun getItemCount(): Int = listPasien.size
}
