package com.fromryan.projectfoodapp.data.datasource.catalog

import com.fromryan.projectfoodapp.data.datasource.catalog.DataSourceFoodCatalog
import com.fromryan.projectfoodapp.data.model.Catalog

class DummyCatalogDataSource : DataSourceFoodCatalog {
    override fun getFoodCatalogItem(): List<Catalog> {
        return mutableListOf(
            Catalog(
                name = "Caprize Pizza",
                category = "pizza",
                description = "Pizza lezat dengan saus tomat segar, keju mozarella, dan topping pilihan.",
                price = 250000.0,
                location = "Jl. Raya Darmo No.79 A, Keputran, Kec. Tegalsari, Surabaya, Jawa Timur 60265",
                image = "https://i.pinimg.com/564x/9b/af/f8/9baff8fb012ab3f0f612f9e04ebed23e.jpg",
            ),
            Catalog(
                category = "pizza",
                name = "Delano Pizza",
                description = "Pizza premium dengan saus tomat khas, keju mozarella, dan topping segar",
                price = 300000.0,
                location = "Jl. Jenderal Basuki Rachmat No.25-31, Embong Kaliasin, Kec. Genteng, Surabaya, Jawa Timur 60271",
                image = "https://i.pinimg.com/564x/b1/4e/96/b14e967be4d2d4d121ec19d6de13ea7c.jpg",
            ),
            Catalog(
                category = "pizza",
                name = "Drougno Pizza",
                description = "Pizza premium dengan saus tomat spesial, keju mozarella impor, dan topping unik.",
                price = 150000.0,
                location = "Jl. Raya Jemursari No.136, Kendangsari, Kec. Tenggilis Mejoyo, Surabaya, Jawa Timur 60292",
                image = "https://i.pinimg.com/564x/b9/a9/6c/b9a96c89d1348347524418f7056d7649.jpg",
            ),
            Catalog(
                category = "pizza",
                name = "Mantappo Pizza",
                description = "Pizza premium dengan saus tomat spesial, keju mozarella impor, dan topping unik.",
                price = 150000.0,
                location = "Jl. Dr. Ir. H. Soekarno No.Kel, Kedung Baruk, Kec. Rungkut, Surabaya, Jawa Timur 60298",
                image = "https://i.pinimg.com/564x/65/b9/91/65b99179801c74ca92e39b41a3c6966b.jpg",
            ),
            Catalog(
                category = "pizza",
                name = "Melerno Pizza",
                description = "Pizza premium dengan saus tomat khas, keju mozzarella, dan daging sapi panggang",
                price = 200000.0,
                location = "Jl. Kenjeran No.455, Gading, Kec. Tambaksari, Surabaya, Jawa Timur 60134",
                image = "https://i.pinimg.com/564x/8f/4d/e6/8f4de68b1c709742e13f7ca94ac089af.jpg",
            ),
            Catalog(
                category = "pizza",
                name = "Pedaso Pizza",
                description = "Pizza lezat dengan saus tomat segar, keju cheddar, dan potongan sosis.",
                price = 220000.0,
                location = "Jl. Raya Dharma Husada Indah No.115 E, Mojo, Kec. Gubeng, Surabaya, Jawa Timur 60285",
                image = "https://i.pinimg.com/564x/d9/25/d7/d925d73e7cf94722a25c478d7a3914d1.jpg",
            ),
            Catalog(
                category = "pizza",
                name = "Mediterranean Pizza",
                description = "Pizza eksotis dengan saus pesto, keju feta, dan olive kalamata.",
                price = 180000.0,
                location = "Jl. Raya Darmo No.79 A, Keputran, Kec. Tegalsari, Surabaya, Jawa Timur 60265 ",
                image = "https://i.pinimg.com/564x/69/2a/55/692a5556a19c2b11665d049db2f45923.jpg",
            ),
        )
    }
}