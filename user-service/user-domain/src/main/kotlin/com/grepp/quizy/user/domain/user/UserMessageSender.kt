package com.grepp.quizy.user.domain.user

interface UserMessageSender {

    fun send(message: UserEvent)
}