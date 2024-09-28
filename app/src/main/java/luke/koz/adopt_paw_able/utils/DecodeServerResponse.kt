package luke.koz.adopt_paw_able.utils

import luke.koz.adopt_paw_able.animals.domain.model.AnimalEntry

data class DecodedAnimalInfo(
    val id: Int,
    val category: String,
    val breed: String?,
    val age: String?,
    val gender: String?
)

interface DecodeServerResponse {

    val categoryMap: Map<Int, String>
        get() = mapOf(
            1 to "Dogs",
            2 to "Cats",
            3 to "Birds",
            4 to "Geckos",
            5 to "Fish"
        )

    val breedMap : Map<Int, Map<Int, String>>
        get() = mapOf(
        0 to mapOf( //empty
            1 to "No Category Selected"
        ),
        1 to mapOf( // Dogs
            1 to "Shiba",
            2 to "Akita",
            3 to "Husky",
            4 to "German Shepperd",
            5 to "Golden Retriever"
        ),
        2 to mapOf( // Cats
            1 to "British",
            2 to "Siamese",
            3 to "American Bobtail",
            4 to "Traditional Persian",
            5 to "Siberian"
        ),
        3 to mapOf( // Birds
            1 to "Parrot",
            2 to "Canary",
            3 to "Finch",
            4 to "Cockatiel",
            5 to "Lovebird"
        ),
        4 to mapOf( // Geckos
            1 to "African Fat Tail Gecko",
            2 to "Tokay Gecko",
            3 to "Madagascar Day Gecko",
            4 to "Gargoyle Gecko",
            5 to "Leopard Gecko"
        ),
        5 to mapOf( // Fish
            1 to "Koi",
            2 to "Betta fish",
            3 to "Glass Catfish",
            4 to "Glofish",
            5 to "Paradise Fish"
        )
        // Add similar mappings for other categories if necessary
    )
    fun decodeAnimalResponse (response: AnimalEntry) : DecodedAnimalInfo{

        fun getBreedName(categoryId: Int, breedId: Int): String {
            val breeds = breedMap[categoryId]
            return breeds?.get(breedId) ?: "Unknown Breed"
        }

        val categoryName = categoryMap[response.category] ?: "Unknown Category"
        val breedName = getBreedName(response.category, response.breed ?: -1)
        val ageDescription = response.age?.let { "$it years old" } ?: "Age not specified"
        val genderDescription = response.gender?.let { if (it) "Male" else "Female" } ?: "Gender not specified"

        return DecodedAnimalInfo(
            id = response.id,
            category = categoryName,
            breed = breedName,
            age = ageDescription,
            gender = genderDescription
        )
    }

    val ageMap : Map<Int, String>
        get() = mapOf(
            1 to "Junior",
            2 to "Adult",
            3 to "Senior"
        )

    val genderMap : Map<Int, String>
        get() = mapOf(1 to "Male", 2 to "Female", 3 to "Unisex")

    val animalSterilizedMap : Map<Int, String>
        get() = mapOf(1 to "Not sterilized", 2 to "Sterilized")

    val animalHouseTrainedMap : Map<Int, String>
        get() = mapOf(1 to "Not house trained", 2 to "House trained")

    val animalDeclawedMap : Map<Int, String>
        get() = mapOf(1 to "Not declawed", 2 to "Declawed")

    val animalFreeMap : Map<Int, String>
        get() = mapOf(1 to "Not free", 2 to "Free")

    val emptyMap : Map<Int, String>
        get() = mapOf(0 to "")

}