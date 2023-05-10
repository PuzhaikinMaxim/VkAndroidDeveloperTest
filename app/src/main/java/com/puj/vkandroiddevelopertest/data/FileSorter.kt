package com.puj.vkandroiddevelopertest.data

import com.puj.vkandroiddevelopertest.domain.SortType
import java.nio.file.Files
import java.nio.file.attribute.FileTime
import javax.inject.Inject

class FileSorter @Inject constructor() {

    fun sort(unsortedList: List<RawFile>, sortType: SortType): List<RawFile> {
        when(sortType){
            SortType.SORT_BY_NAME_DESC -> {
                return unsortedList.sortedByDescending { it.file.name }
            }
            SortType.SORT_BY_NAME_ASC -> {
                return unsortedList.sortedBy { it.file.name }
            }
            SortType.SORT_BY_SIZE_DESC -> {
                return unsortedList.sortedByDescending { it.file.length() }
            }
            SortType.SORT_BY_SIZE_ASC -> {
                return unsortedList.sortedBy { it.file.length() }
            }
            SortType.SORT_BY_CREATION_DATE_DESC -> {
                return unsortedList.sortedByDescending {
                    val creationTime = Files.getAttribute(
                        it.file.toPath(),
                        "creationTime"
                    ) as FileTime
                    creationTime.toMillis()
                }
            }
            SortType.SORT_BY_CREATION_DATE_ASC -> {
                return unsortedList.sortedByDescending {
                    val creationTime = Files.getAttribute(
                        it.file.toPath(),
                        "creationTime"
                    ) as FileTime
                    creationTime.toMillis()
                }
            }
            SortType.SORT_BY_EXTENSION_DESC -> {
                return unsortedList.sortedByDescending { it.file.extension }
            }
            SortType.SORT_BY_EXTENSION_ASC -> {
                return unsortedList.sortedByDescending { it.file.extension }
            }
        }
    }
}