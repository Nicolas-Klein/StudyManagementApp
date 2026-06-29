package com.example.studymanagementapp.storage

import android.content.Context
import com.example.studymanagementapp.data.TaskDeadline
import com.example.studymanagementapp.data.TaskForDay

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import androidx.core.content.edit
import android.content.SharedPreferences

/**
 * Verwaltet die persitente Speicherung und das Laden von Anwendungsdaten.
 * Nutzt [SharedPreferences] für die lokale Speicherung und [Gson] für die Serialisierung / Deserialisierung von Objektlisten in Json-Strings.
 *
 * @param context Der [Context] der Anwendung, der für den Zugriff auf die SharedPreferences benötigt wird.
 */
class StorageManager(context: Context) {

    /** Instanz der SharedPreferences mit privatem Zugriff (nur für diese App lesbar). */
    private val sharedPreferences = context.getSharedPreferences("PlannerPrefs", Context.MODE_PRIVATE)
    /** Gson-Parser für die Konvertierung von Objekten in JSON und umgekehrt. */
    private val gson = Gson()

    /**
     * Konvertiert die übergebene Liste von täglichen Aufgaben ([TaskForDay]) in ein JSON-Format und speichert sie dauerhaft in den SharedPreferences ab.
     *
     * @param tasks Die Liste der Aufgaben die gepseichert werden sollen.
     */
    fun saveTodoTasks(tasks: List<TaskForDay>) {
        val jsonString = gson.toJson(tasks)
        sharedPreferences.edit { putString("todo_tasks", jsonString) }
    }

    /**
     * Lädt die gespeicherten täglichen Aufgaben aus den SharedPreferences und stellt diese wieder als Liste von [TaskForDay]-Objekten her.
     *
     * @return Eine Liste der gespeicherten Aufgaben oder eine [emptyList], falls noch keine Aufgaben existieren.
     */
    fun loadTodoTasks(): List<TaskForDay> {
        val jsonString = sharedPreferences.getString("todo_tasks", null) ?: return emptyList()
        val type = object : TypeToken<List<TaskForDay>>() {}.type
        return gson.fromJson(jsonString, type)
    }

    /**
     * Konvertiert die übergebene Liste von Deadlines ([TaskDeadline]) in ein JSON-Format und speichert sie dauerhaft in den SharedPreferences ab.
     *
     *  @param tasks Die Liste der zu speichernden Deadlines.
     */
    fun saveDeadlines(tasks: List<TaskDeadline>) {
        val jsonString = gson.toJson(tasks)
        sharedPreferences.edit { putString("deadline_task", jsonString) }
    }

    /**
     * Lädt die gespeicherten Deadlines aus den SharedPreferences und stellt diese wieder als Liste von [TaskDeadline]-Objekten her.
     *
     * @return Eine Liste der gespeicherten Deadlines oder eine [emptyList], falls noch keine Deadlines existieren.
     */
    fun loadDeadlines(): List<TaskDeadline> {
        val jsonString = sharedPreferences.getString("deadline_task", null) ?: return emptyList()
        val type = object : TypeToken<List<TaskDeadline>>() {}.type
        return gson.fromJson(jsonString, type)
    }

}