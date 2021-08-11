package com.bragonya.unsplashdemoapp.utils

import com.bragonya.unsplashdemoapp.models.ImageRoot
import com.bragonya.unsplashdemoapp.models.ProfileImage
import com.bragonya.unsplashdemoapp.models.Urls
import com.bragonya.unsplashdemoapp.models.User

object Fakes {
    fun getDummyUser() = ImageRoot(
        "",
        "",
        "Instagram: @cityturtles All images edited with @cityturtlespresets www.cityturtlespresets.com Every purchase/Paypal donation helps us continue to create content for you ◡̈  Thank you for reading & have a lovely rest of your day",
        null,
        Urls(
            "",
            "",
            "https://images.unsplash.com/photo-1628383657590-e6bab094d913?crop=entropy&cs=tinysrgb&fit=max&fm=jpg&ixid=MnwyNTA4MDJ8MHwxfGFsbHwxN3x8fHx8fDJ8fDE2Mjg0ODc4ODE&ixlib=rb-1.2.1&q=80&w=400"
        ),
        10,
        User(
            "Bragonyita",
            "Brayan Yaquian",
            ProfileImage(
                "https://images.unsplash.com/profile-1588262339016-ec5fb24daef6image?ixlib=rb-1.2.1&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32",
            )
        )
    )
}