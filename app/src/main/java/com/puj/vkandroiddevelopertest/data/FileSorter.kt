package com.puj.vkandroiddevelopertest.data

import com.puj.vkandroiddevelopertest.domain.SortType
import java.io.File
import java.nio.file.Files
import java.nio.file.attribute.FileTime
import javax.inject.Inject

class FileSorter @Inject constructor() {

    fun sort(unsortedList: List<File>, sortType: SortType): List<File> {
        when(sortType){
            SortType.SORT_BY_NAME_DESC -> {
                return unsortedList.sortedByDescending { it.name }
            }
            SortType.SORT_BY_NAME_ASC -> {
                return unsortedList.sortedBy { it.name }
            }
            SortType.SORT_BY_SIZE_DESC -> {
                return unsortedList.sortedByDescending { it.length() }
            }
            SortType.SORT_BY_SIZE_ASC -> {
                return unsortedList.sortedBy { it.length() }
            }
            SortType.SORT_BY_CREATION_DATE_DESC -> {
                return unsortedList.sortedByDescending {
                    val creationTime = Files.getAttribute(
                        it.toPath(),
                        "creationTime"
                    ) as FileTime
                    creationTime.toMillis()
                }
            }
            SortType.SORT_BY_CREATION_DATE_ASC -> {
                return unsortedList.sortedByDescending {
                    val creationTime = Files.getAttribute(
                        it.toPath(),
                        "creationTime"
                    ) as FileTime
                    creationTime.toMillis()
                }
            }
            SortType.SORT_BY_EXTENSION_DESC -> {
                return unsortedList.sortedByDescending { it.extension }
            }
            SortType.SORT_BY_EXTENSION_ASC -> {
                return unsortedList.sortedByDescending { it.extension }
            }
        }
    }
}