package com.example.arcane.data.repository

import com.example.arcane.data.remote.api.GeminiService
import com.example.arcane.data.remote.api.SystemPrompts
import com.example.arcane.domain.repository.AIRepository

class AIRepositoryImpl(private val geminiService: GeminiService) : AIRepository {

    override suspend fun summarizeLiterature(bookTitle: String, bookDescription: String): Result<String> {
        val prompt = """
            Judul Buku: $bookTitle
            
            Deskripsi/Sinopsis: $bookDescription
            
            Tolong buat ringkasan komprehensif buku ini yang mencakup argumen utama, tema sentral, dan kontribusinya terhadap bidang pengetahuan.
        """.trimIndent()
        return geminiService.generateContent(prompt = prompt, systemPrompt = SystemPrompts.LITERATURE_SUMMARIZER)
    }

    override suspend fun analyzeMethodology(bookTitle: String, bookDescription: String): Result<String> {
        val prompt = """
            Judul Buku: $bookTitle
            
            Deskripsi/Sinopsis: $bookDescription
            
            Analisis pendekatan metodologi, kerangka teoritis, dan strategi argumentasi yang digunakan dalam buku ini.
        """.trimIndent()
        return geminiService.generateContent(prompt = prompt, systemPrompt = SystemPrompts.METHODOLOGY_ANALYZER)
    }

    override suspend fun connectConcepts(bookTitle: String, bookDescription: String): Result<String> {
        val prompt = """
            Judul Buku: $bookTitle
            
            Deskripsi/Sinopsis: $bookDescription
            
            Hubungkan konsep-konsep dalam buku ini dengan teori dan literatur yang lebih luas di bidang terkait.
        """.trimIndent()
        return geminiService.generateContent(prompt = prompt, systemPrompt = SystemPrompts.CONCEPT_CONNECTOR)
    }

    override suspend fun generateDiscussionQuestions(bookTitle: String, bookDescription: String): Result<String> {
        val prompt = """
            Judul Buku: $bookTitle
            
            Deskripsi/Sinopsis: $bookDescription
            
            Hasilkan pertanyaan diskusi kritis dan mendalam untuk membahas buku ini dalam konteks akademis.
        """.trimIndent()
        return geminiService.generateContent(prompt = prompt, systemPrompt = SystemPrompts.DISCUSSION_QUESTIONS)
    }

    override suspend fun chat(bookTitle: String, bookDescription: String, question: String): Result<String> {
        val prompt = """
            Konteks Buku:
            Judul: $bookTitle
            Deskripsi: $bookDescription
            
            Pertanyaan Pengguna: $question
        """.trimIndent()
        return geminiService.generateContent(prompt = prompt, systemPrompt = SystemPrompts.RESEARCH_ASSISTANT)
    }
}
