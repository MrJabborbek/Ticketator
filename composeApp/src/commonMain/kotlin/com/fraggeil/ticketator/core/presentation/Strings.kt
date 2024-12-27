package com.fraggeil.ticketator.core.presentation

import kotlinx.coroutines.flow.MutableStateFlow

const val UZ = "uz"
const val LANG = "language"
const val UZ_CYR = "kk"
const val RU = "ru"
const val EN = "en"
const val DEFAULT_APP_LANGUAGE = UZ
const val DEFAULT_APP_LANGUAGE_NAME = "O'zbekcha"

sealed class Language(val lang: String, val langCode: String) {
    object English : Language(
        lang = "English", langCode = EN
    )

    object Russian : Language(
        lang = "Русский", langCode = RU
    )

    object Uzbek : Language(
        lang = "O'zbekcha", langCode = UZ
    )

    object UzbekCyr : Language(
        lang = "Ўзбекча", langCode = UZ_CYR
    )

    object Default : Language(
        lang = DEFAULT_APP_LANGUAGE_NAME, langCode = DEFAULT_APP_LANGUAGE
    )

    companion object {
        fun getLangCodeByLang(lang: String) = when (lang) {
            "English" -> EN
            "Русский" -> RU
            "O'zbekcha" -> UZ
            "Ўзбекча" -> UZ_CYR
            else -> Default.langCode
        }

        fun getLangByLangCode(langCode: String) = when (langCode) {
            EN -> "English"
            RU -> "Русский"
            UZ -> "O'zbekcha"
            UZ_CYR -> "Ўзбекча"
            else -> Default.lang
        }

        fun getLanguageByLangCode(langCode: String) = when (langCode) {
            EN -> English
            RU -> Russian
            UZ -> Uzbek
            UZ_CYR -> UzbekCyr
            else -> Default
        }

            val entries: List<Language>
            get() = listOf(Uzbek, UzbekCyr, English, Russian)

    }
}
sealed class StringItem(
    val valueUz: String = "",
    val valueEn: String = "",
    val valueUzCyr: String = "",
    val valueRu: String = "",
)

object Strings{
    var selectedLanguage = MutableStateFlow<Language>(Language.Uzbek)
    fun StringItem.value():String{
        return when(selectedLanguage.value){
            Language.Uzbek -> valueUz
            Language.UzbekCyr -> valueUzCyr
            Language.English -> valueEn
            Language.Russian -> valueRu
            else -> valueUz
        }
    }

    data object AppName : StringItem(valueUz = "Ticketator", valueEn = "Ticketator", valueUzCyr = "Ticketator", valueRu = "Ticketator")
    data object Categories : StringItem(valueUz = "Kategoriyalar", valueEn = "Categories", valueUzCyr = "Категориялар", valueRu = "Категории")
    data object Cart : StringItem(valueUz = "Savat", valueEn = "Cart", valueUzCyr = "Сават", valueRu = "Корзина")
    data object ShowAll : StringItem(valueUz = "Hammasi", valueEn = "Show all", valueUzCyr = "Ҳаммаси", valueRu = "Показать все")
    data object Brands : StringItem(valueUz = "Brendlar", valueEn = "Brands", valueUzCyr = "Брендлар", valueRu = "Бренды")
    data object Brand : StringItem(valueUz = "Brend", valueEn = "Brand", valueUzCyr = "Бренд", valueRu = "Бренд")
    data object Catalogue : StringItem(valueUz = "Katalog", valueEn = "Catalogue", valueUzCyr = "Каталог", valueRu = "Каталог")
    data object Services : StringItem(valueUz = "Xizmatlar", valueEn = "Services", valueUzCyr = "Хизматлар", valueRu = "Услуги")
    data object Partnering : StringItem(valueUz = "Hamkorlik", valueEn = "Partnering", valueUzCyr = "Ҳамкорлик", valueRu = "Партнерство")
    data object Useful: StringItem(valueUz = "Foydali", valueEn = "Useful", valueUzCyr = "Фойдали", valueRu = "Полезный")
    data object January: StringItem(valueUz = "Yanvar", valueEn = "January", valueUzCyr = "Январь", valueRu = "Январь")
    data object February: StringItem(valueUz = "Fevral", valueEn = "February", valueUzCyr = "Февраль", valueRu = "Февраль")
    data object March: StringItem(valueUz = "Mart", valueEn = "March", valueUzCyr = "Март", valueRu = "Март")
    data object April: StringItem(valueUz = "Aprel", valueEn = "April", valueUzCyr = "Апрель", valueRu = "Апрель")
    data object May: StringItem(valueUz = "May", valueEn = "May", valueUzCyr = "Май", valueRu = "Май")
    data object June: StringItem(valueUz = "Iyun", valueEn = "June", valueUzCyr = "Июнь", valueRu = "Июнь")
    data object July: StringItem(valueUz = "Iyul", valueEn = "July", valueUzCyr = "Июль", valueRu = "Июль")
    data object August: StringItem(valueUz = "Avgust", valueEn = "August", valueUzCyr = "Август", valueRu = "Август")
    data object September: StringItem(valueUz = "Sentabr", valueEn = "September", valueUzCyr = "Сентябрь", valueRu = "Сентябрь")
    data object October: StringItem(valueUz = "Oktabr", valueEn = "October", valueUzCyr = "Октябрь", valueRu = "Октябрь")
    data object November: StringItem(valueUz = "Noyabr", valueEn = "November", valueUzCyr = "Ноябрь", valueRu = "Ноябрь")
    data object December: StringItem(valueUz = "Dekabr", valueEn = "December", valueUzCyr = "Декабрь", valueRu = "Декабрь")
    data object UnknownErrorOccurred: StringItem(valueUz = "Noma'lum xato yuz berdi", valueEn = "Unknown error occurred", valueUzCyr = "Номаълум хатолик содир бўлди", valueRu = "Произошла неизвестная ошибка")
    data object Unknown: StringItem(valueUz = "Noma'lum", valueEn = "Unknown", valueUzCyr = "Номаълум", valueRu = "Неизвестный")
    data object Favourites: StringItem(valueUz = "Sevimlilar", valueEn = "Favourites", valueUzCyr = "Севимлилар", valueRu = "Избранное")
    data object SearchProduct: StringItem(valueUz = "Mahsulot qidirish", valueEn = "Search product", valueUzCyr = "Маҳсулот қидириш", valueRu = "Поиск продукта")
    data object Soon: StringItem(valueUz = "Tez kunda", valueEn = "Soon", valueUzCyr = "Тез кунда", valueRu = "Скоро")
    data object New: StringItem(valueUz = "Yangi", valueEn = "New", valueUzCyr = "Янги", valueRu = "Новый")
    data object WithVAT: StringItem(valueUz = "QQS bilan", valueEn = "with VAT", valueUzCyr = "ҚҚС билан", valueRu = "с НДС")
    data object Y_E: StringItem(valueUz = "y.e", valueEn = "y.e", valueUzCyr = "y.е", valueRu = "y.е")
    data object AvailableInSale: StringItem(valueUz = "Sotuvda mavjud", valueEn = "Available in sale", valueUzCyr = "Сотвда мавжуд", valueRu = "В продаже")
    data object FullDescription: StringItem("To'liq tavsif", valueEn = "Full description", valueUzCyr = "Тўлиқ тавсиф", valueRu = "Полное описание")
    data object UploadDatasheet: StringItem(valueUz = "Datashitni yuklab olish", valueEn = "Upload datasheet", valueUzCyr = "Даташитни юклаб олиш", valueRu = "Загрузить даташит")
    data object ToCart: StringItem(valueUz = "Savatga", valueEn = "To cart", valueUzCyr = "Саватга", valueRu = "В корзину")
    data object NothingFound: StringItem(valueUz = "Afsus, hech nima topilmadi. :(", valueEn = "Oops, nothing found. :(", valueUzCyr = "Афсус, ҳеч нима топилмади. :(", valueRu = "Упс, ничего не найдено. :(")
    data object ReadySolutions: StringItem(valueUz = "Tayyor yechimlar", valueEn = "Ready solutions", valueUzCyr = "Тайёр ечимлар", valueRu = "Готовые решения")
    data object YourOrder: StringItem(valueUz = "Sizning buyurtmangiz", valueEn = "Your order", valueUzCyr = "Сизнинг буюртмангиз", valueRu = "Ваш заказ")
    data object _Products: StringItem(valueUz = "ta mahsulot", valueEn = "products", valueUzCyr = "та маҳсулот", valueRu = "товаров")
    data object DiscountOnProducts: StringItem(valueUz = "Mahsulotlarga chegirma", valueEn = "Discount on products", valueUzCyr = "Маҳсулотларга чегрма", valueRu = "Скидка на товары")
    data object Total: StringItem(valueUz = "Jami:", valueEn = "Total:", valueUzCyr = "Жами:", valueRu = "Итого:")
    data object Ordering: StringItem(valueUz = "Buyurtma berish", valueEn = "Ordering", valueUzCyr = "Бюртма бериш", valueRu = "Заказать")
    data object Delivering: StringItem(valueUz = "Yetkazib berish", valueEn = "Delivering", valueUzCyr = " Етказиб бериш", valueRu = "Доставлять")
    data object PickUp:
        StringItem(valueUz = "Olib ketish", valueEn = "Pick up", valueUzCyr = "Олиб кетиш", valueRu = "Самовывоз")
    data object DeliveryMethod: StringItem(valueUz = "Yetkazib berish turi", valueEn = "Delivery method", valueUzCyr = "Yetкизиб бериш тури", valueRu = "Способ доставки")
    data object Client:
        StringItem(valueUz = "Mijoz", valueEn = "Client", valueUzCyr = "Мижоз", valueRu = "Клиент")
    data object PhysPerson: StringItem(valueUz = "Jis. shaxs", valueEn = "Phys. person", valueUzCyr = "Jis. shaxs", valueRu = "Физ. лицо")
    data object LegalEntity: StringItem(valueUz = "Yur. shaxs", valueEn = "Legal entity", valueUzCyr = "Юр. шахс", valueRu = "Юр. лицо")
    data object FullName: StringItem(valueUz = "F.I.SH", valueEn = "Full name", valueUzCyr = "Ф.И.Ш", valueRu = "И.Ф.О")
    data object Pinfl: StringItem(valueUz = "JSHSHIR", valueEn = "PINFL", valueUzCyr = "ЖШШИР", valueRu = "ПИНФЛ")
    data object PassportNumber: StringItem(valueUz = "Pasport seriya va raqami", valueEn = "Passport serial number", valueUzCyr = "Паспорт серия ва рақами", valueRu = "Серия и номер паспорта")
    data object PhoneNumber: StringItem(valueUz = "Telefon raqami", valueEn = "Phone number", valueUzCyr = "Телефон рақами", valueRu = "Номер телефона")
    data object CompanyName: StringItem(valueUz = "Tashkilot nomi", valueEn = "Company name", valueUzCyr = "Тащкилот номи", valueRu = "Название компании")
    data object TIN: StringItem(valueUz = "STIR", valueEn = "TIN", valueUzCyr = "СТИР", valueRu = "ИНН")
    data object FullNameOfDirector: StringItem(valueUz = "Direktorning F.I.SH", valueEn = "Full name of director", valueUzCyr = "Директорнинг Ф.И.Ш", valueRu = "ФИО директора")
    data object Bank: StringItem(valueUz = "Bank", valueEn = "Bank", valueUzCyr = "Банк", valueRu = "Банк")
    data object MFO: StringItem(valueUz = "MFO", valueEn = "MFO", valueUzCyr = "МФО", valueRu = "МФО")
    data object AccountNumber: StringItem(valueUz = "Hisob raqami", valueEn = "Account number", valueUzCyr = "Ҳисоб рақами", valueRu = "Номер счета")
    data object OKED: StringItem(valueUz = "OKED", valueEn = "OKED", valueUzCyr = "ОКЭД", valueRu = "ОКЭД")
    data object JuridicalAddress: StringItem(valueUz = "Yuridik manzil", valueEn = "Juridical address", valueUzCyr = "Юридик манзил", valueRu = "Юридический адрес")
    data object Address: StringItem(valueUz = "Manzil", valueEn = "Address", valueUzCyr = "Манзил", valueRu = "Адрес")
    data object Apartment: StringItem(valueUz = "Uy", valueEn = "Apartment", valueUzCyr = "Uy", valueRu = "Квартира")
    data object Landmark: StringItem(valueUz = "Mo'ljal", valueEn = "Landmark", valueUzCyr = "Молжайл", valueRu = "Орентир")
    data object Region: StringItem(valueUz = "Viloyat", valueEn = "Region", valueUzCyr = "Viloyat", valueRu = "Область")
    data object CityOrTown: StringItem(valueUz = "Tuman/shahar", valueEn = "City or town", valueUzCyr = "Туман/шахар", valueRu = "Город/Район")
    data object Installation: StringItem(valueUz = "O‘rnatish", valueEn = "Installation", valueUzCyr = "Ўрнатш", valueRu = "Установление")
    data object InstallMyself: StringItem(valueUz = "O'zim o'rnatib olaman", valueEn = "I can install it myself", valueUzCyr = "Ўзим ўрнатиб оламан", valueRu = "Я смогу установить")
    data object WithInstallation: StringItem(valueUz = "O'rnatish bilan", valueEn = "With installation", valueUzCyr = "Ўрнатиш билан", valueRu = "С установкой")
    data object PickupPoint: StringItem(valueUz = "Olib ketish punkti", valueEn = "Pickup point", valueUzCyr = "Олиб кетиш пункти", valueRu = "Точка самовывоза")
    data object Checkout: StringItem(valueUz = "To'lov", valueEn = "Checkout", valueUzCyr = "Тўлов", valueRu = "Оплата")
    data object PaymentMethod: StringItem(valueUz = "To'lov turi", valueEn = "Payment method", valueUzCyr = "Тўлов turi", valueRu = "Способ оплаты")
    data object PaymentSystem: StringItem(valueUz = "To'lov tizimi", valueEn = "Payment system", valueUzCyr = "Тўлов тизими", valueRu = "Платежная система")
    data object PaymentSystems: StringItem(valueUz = "To'lov tizimlari", valueEn = "Payment system", valueUzCyr = "Тўлов тизимлари", valueRu = "Платежние системы")
    data object Cash: StringItem(valueUz = "Naqd pul", valueEn = "Cash", valueUzCyr = "Нақд пул", valueRu = "Наличные")
    data object MakeAContract: StringItem(valueUz = "Shartnoma tuzish", valueEn = "Make a contract", valueUzCyr = "Шартнома тузиш", valueRu = "Сделать договор")
    data object Contract: StringItem(valueUz = "Shartnoma", valueEn = "Contract", valueUzCyr = "Шартнома", valueRu = "Договор")
    data object Pay: StringItem(valueUz = "To'lash", valueEn = "Pay", valueUzCyr = "Тўлаш", valueRu = "Оплатить")
    data object ByPayingCashText: StringItem(valueUz = "Naqd pul bilan to'lashda to'lovingiz 24 soatdan keyin tasdiqlanadi. Bank orqali tekshiruv hamda tasdiqlanishi uchun.", valueEn = "When paying by cash, your payment will be confirmed after 24 hours. For verification and approval through the bank.", valueUzCyr = "Нақд пул билан тўлашда тўловингиз 24 соатдан кейин тасдиқланади. Банк орқали текширув ҳамда тасдиқланиши учун.", valueRu = "При оплате наличными ваш платеж будет подтвержден через 24 часа. Для проверки и одобрения через банк.")
    data object Close: StringItem(valueUz = "Yopish", valueEn = "Close", valueUzCyr = "Ёпиш", valueRu = "Закрыт")
    data object OrderCreatedSuccessfully: StringItem(valueUz = "Buyurtma muvaffaqiyatli qabul qilindi", valueEn = "Order created successfully", valueUzCyr = "Бюртма муваффақиятли қабул қилинди", valueRu = "Заказ успешно создан")
    data object OrderNumber: StringItem(valueUz = "Buyurtma raqami", valueEn = "Order number", valueUzCyr = "Бюртма рақами", valueRu = "Номер заказа")
    data object DownloadTheContract: StringItem(valueUz = "Shartnomani yuklab olish", valueEn = "Download the contract", valueUzCyr = "Шартномани юклаб олиш", valueRu = "Скачать договор")
    data object AnErrorOccurred: StringItem(valueUz = "Xatolik yuz berdi", valueEn = "An error occurred", valueUzCyr = "Хатолик юз берди", valueRu = "Произошла ошибка")
    data object CallViaPhone: StringItem(valueUz = "Telefon orqali murojaat qilish", valueEn = "Call via phone", valueUzCyr = "Телефон орқали мурожаат қилиш", valueRu = "Звонить по телефону")
    data object ContactViaTelegram: StringItem(valueUz = "Telegram orqali bog'lanish", valueEn = "Contact via Telegram", valueUzCyr = "Телеграм орқали боғланиш", valueRu = "Связаться через Telegram")
    data object ThereIsNothingInYourCartYet: StringItem(valueUz = "Savatingizda hali biror narsa yo‘q",  valueEn = "There is nothing in your cart yet", valueUzCyr = "Саватингизда ҳали бирор нарса йўқ", valueRu = "В вашей корзине пока нет товаров")
    data object StartFromHomePageText: StringItem(valueUz = "Asosiy sahifadan boshlang yoki kerakli mahsulotni toping", valueEn = "Start from home page or select a product", valueUzCyr = "Асосий саҳифадан бошланг ёки керакли маҳсулотни топинг", valueRu = "Начните с главной страницы или найдите нужный вам товар")
    data object HomePage: StringItem(valueUz = "Asosiy sahifa", valueEn = "Main page", valueUzCyr = "Асосий саҳифа", valueRu = "Главная страница")
    data object MyOrders: StringItem(valueUz = "Mening buyurtmalarim", valueEn = "My orders", valueUzCyr = "Менинг буюртмаларим", valueRu = "Мои заказы")
    data object OrderNo: StringItem(valueUz = "Buyurtma №", valueEn = "Order Num:", valueUzCyr = "Буюртма №", "Заказ №")
    data object CreatedTime: StringItem(valueUz = "Yaratilgan vaqti", valueEn = "Created time", valueUzCyr = "Яратилган вақти", valueRu = "Время создания")
    data object OrderPrice: StringItem(valueUz = "Buyurtma narxi", valueEn = "Order price", "Буюртма нархи", "Сумма заказа")
    data object OrderDetails: StringItem(valueUz = "Buyurtma tafsilotlari", valueEn = "Order details", "Буюууртма тафсилотлари", valueRu = "Детали заказа")
    data object OrderStatus: StringItem(valueUz = "Buyurtma holati", valueEn = "Order status", "Буюууртма ҳолати", "Статус заказа")
    data object Online: StringItem(valueUz = "Online", valueEn = "Online", valueUzCyr = "Онлайн", valueRu = "Онлайн")
    data object Click: StringItem(valueUz = "Click", valueEn = "Click", valueUzCyr = "Клик", valueRu = "Клик")
    data object Payme: StringItem(valueUz = "Payme", valueEn = "Payme", valueUzCyr = "Пеймэ", valueRu = "Пеймэ")
    data object ShoppingList: StringItem(valueUz = "Xaridlar ro'yhati", valueEn = "Shopping list", valueUzCyr = "Харидлар рўйҳати", "Список покупок")
    data object CustomerInformation: StringItem("Mijoz ma'lumotlari", valueEn = "Customer information", valueUzCyr = "Мижоз маълумотлари", valueRu = "Информация о клиенте")
    data object CustomerType: StringItem(valueUz = "Mijoz turi", valueEn = "Customer type", valueUzCyr = "Мижоз тури", valueRu = "Тип клиента")
    data object DeliveryInformation: StringItem(valueUz = "Yetkazib berish tafsiloti", valueEn = "Delivery information", valueUzCyr = "Етказиб бериш тафсилотлари", valueRu = "Информация о доставке")
    data object DeliveryType: StringItem(valueUz = "Yetkazib berish turi", valueEn = "Delivery type", valueUzCyr = "Етказиб бериш тури", valueRu = "Способ доставки")
    data object MyApplications: StringItem(valueUz = "Mening arizalarim", valueEn = "My applications")
    data object ContactWithUs: StringItem(valueUz = "Biz bilan bog'lanish", valueEn = "Contact with us")
    data object YouAreNotLoggedIn: StringItem(valueUz = "Siz hali tizimga kirmagansiz", valueEn = "You are not logged in yet")
    data object LoginToContinue: StringItem(valueUz = "Davom etish uchun tizimga kiring", valueEn = "Log in to continue")
    data object Login: StringItem(valueUz = "Kirish", valueEn = "Login")
    data object LogOut: StringItem(valueUz = "Chiqish", valueEn = "Log out")
    data object Language_: StringItem(valueUz = "Til", valueEn = "Language")
    data object AboutApp: StringItem(valueUz = "Ilova haqida", valueEn = "About app")
    data object TermsAndConditions: StringItem(valueUz = "Oferta va foydalanish shartlari", valueEn = "Terms and conditions")
    data object EnterYourPhoneNumberToLogIn: StringItem(valueUz = "Kirish uchun telefon raqamingizni kiriting", valueEn = "Enter your phone number to login")
    data object ByAuthorisationYouAgreeText: StringItem(valueUz = "Avtorizatsiyadan o'tish oraqali siz", "By authorizing,")
    data object YouWillAgreeText: StringItem(valueUz = "Oferta va foydalanish shartlariga o'z roziligingizni bildirasiz", "you agree to the Terms and conditions")
    data object Settings: StringItem(valueUz = "Sozlamalar", valueEn = "Settings")
    data object EditProfile: StringItem(valueUz = "Profilni tahrirlash", valueEn = "Edit profile")
    data object FirstName: StringItem(valueUz = "Ism", valueEn = "First name")
    data object MiddleName: StringItem(valueUz = "Sharif", valueEn = "Middle name")
    data object LastName: StringItem(valueUz = "Familiya", valueEn = "Last name")
    data object DeleteProfile: StringItem(valueUz = "Profilni o'chirish", valueEn = "Delete profile")
    data object Save: StringItem(valueUz = "Saqlash", valueEn = "Save")
    data object Cancel: StringItem(valueUz = "Bekor qilish", valueEn = "Cancel")
    data object Select: StringItem(valueUz = "Tanlash", valueEn = "Select")
    data object UsefulVideos: StringItem(valueUz = "Foydali videolar", valueEn = "Useful videos")
    data object UsefulDocuments: StringItem(valueUz = "Foydali hujjatlar", valueEn = "Useful documents")
    data object LawsAndRegulations: StringItem(valueUz = "Qonun va qoidalar", valueEn = "Laws and regulations")
    data object Home: StringItem(valueUz = "Asosiy", valueEn = "Home")
    data object Location: StringItem(valueUz = "Joylashuv", valueEn = "Location")
    data object Profile: StringItem(valueUz = "Profil", valueEn = "Profile")
    data object ProductDetails: StringItem(valueUz = "Mahsulot tavsifi", valueEn = "Product details")
    data object OtpSentText1: StringItem(
        valueUz = "",
        valueEn = "SMS message has been sent to number "
    )
    data object OtpSentText2: StringItem(
        valueUz = " raqamiga SMS xabar yuborildi. Bir martalik maxfiy kodni kiriting.",
        valueEn = ". Enter one-time code."
    )
    data object ResendIn: StringItem("Qayta jo'natish", valueEn = "Resend in")
    data object ServiceDetails: StringItem(valueUz = "Xizmat tafsilotlari", valueEn = "Service details")
    data object PartneringDetails: StringItem(valueUz = "Hamkorlik tafsilotlari", valueEn = "Partnering details")
    data object Capacity: StringItem(valueUz = "Quvvat", valueEn = "Capacity")
    data object Description: StringItem(valueUz = "Izoh", valueEn = "Description")
    data object Issue: StringItem("Muammo", "Issue")
    data object SubmitAnApplication: StringItem("Ariza yuborish", "Submit an application")
    data object YourApplicationHasBeenSubmitted: StringItem("Arizangiz qabul qilindi", "Your application has been submitted")
    data object ApplicationNumber: StringItem("Ariza raqami", "Application number")
    data object KW: StringItem(valueUz = "kVt", valueEn = "kW")
    data object ApplicationDetails: StringItem(valueUz = "Ariza tafsilotlari", valueEn = "Application details")
    data object ApplicationType: StringItem(valueUz = "Ariza turi", valueEn = "Application type")
    data object Popular:  StringItem(valueUz = "Mashhur", valueEn = "Popular")
    data object CheapestFirst: StringItem(valueUz = "Avval arzonlari", valueEn = "Cheapest first")
    data object ExpensiveFirst: StringItem(valueUz = "Avval qimmatlari", valueEn = "Expensive first")
    data object NewComers: StringItem(valueUz = "Yangi kelganlar", valueEn = "New comers")
    data object From: StringItem(valueUz = "dan", valueEn = "from")
    data object To: StringItem(valueUz = "gacha", valueEn = "to")
    data object Sort: StringItem(valueUz = "Saralash", valueEn = "Sort")
    data object Price: StringItem(valueUz = "Narx", valueEn = "Price")

}