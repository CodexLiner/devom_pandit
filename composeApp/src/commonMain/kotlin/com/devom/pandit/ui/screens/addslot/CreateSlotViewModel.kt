package com.devom.pandit.ui.screens.addslot

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devom.Project
import com.devom.models.slots.CreatePanditSlotInput
import com.devom.models.slots.Slot
import com.devom.network.getUser
import com.devom.utils.Application
import com.devom.utils.network.onResult
import com.devom.utils.network.withError
import com.devom.utils.network.withLoading
import com.devom.utils.network.withSuccessWithoutData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalTime

class CreateSlotViewModel : ViewModel() {


    private val _slots = MutableStateFlow(listOf<Slot>())
    val slots = _slots

    init {
        getAvailableSlots()
    }

    fun getAvailableSlots() {
        viewModelScope.launch {
            Project.pandit.getAvailableSlotsUseCase.invoke(getUser().userId.toString()).collect {
                it.onResult {
                    this@CreateSlotViewModel.viewModelScope.launch {
                        _slots.emit(
                            it.data.mapNotNull { slotDto ->
                                val safeStart =
                                    runCatching { LocalTime.parse(slotDto.startTime) }.getOrNull()
                                val safeEnd =
                                    runCatching { LocalTime.parse(slotDto.endTime) }.getOrNull()
                                if (safeStart != null && safeEnd != null && safeStart.hour in 0..23 && safeEnd.hour in 0..23) {
                                    Slot(
                                        availableDate = slotDto.availableDate,
                                        startTime = slotDto.startTime,
                                        endTime = slotDto.endTime,
                                        id = slotDto.id,
                                        panditId = slotDto.panditId,
                                        isBooked = slotDto.isBooked,
                                        createdAt = slotDto.createdAt,
                                        updatedAt = slotDto.updatedAt
                                    )
                                } else null
                            }
                        )
                    }
                }
            }
        }
    }

    fun setAvailableSlots(slots: List<Slot>) {
        _slots.value = slots
    }

    fun createPanditSlot(slots: List<Slot>) {
        viewModelScope.launch {
            Project.pandit.createPanditSlotUseCase.invoke(
                CreatePanditSlotInput(
                    panditId = getUser().userId,
                    slots = slots
                )
            ).collect {
                it.withLoading {
                    Application.showLoader()
                }
                it.withError {
                    Application.showToast(it.message)
                    Application.hideLoader()
                }
                it.withSuccessWithoutData {
                    Application.hideLoader()
                    setAvailableSlots(slots)
                    getAvailableSlots()
                }
            }
        }
    }

    fun removePanditSlot(slot: Slot) {
        viewModelScope.launch {
            Project.pandit.removePanditSlotUseCase.invoke(slot.id).collect {
                it.withLoading {
                    Application.showLoader()
                }
                it.withError {
                    Application.showToast(it.message)
                    Application.hideLoader()
                }
                it.withSuccessWithoutData {
                    Application.hideLoader()
                    _slots.value = _slots.value.filter { it.id != slot.id }
                    getAvailableSlots()
                }
            }
        }
    }
}