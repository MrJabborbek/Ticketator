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
    data object SelectDate: StringItem(valueUz = "Sanani tanlang", valueEn = "Select date", valueUzCyr = "Санани танланг", valueRu = "Выберите дату")
    data object DayShort: StringItem(valueUz = "k.", valueEn = "d.", valueUzCyr = "к.", valueRu = "к.")
    data object HourShort: StringItem(valueUz = "so.", valueEn = "h.", valueUzCyr = "с.", valueRu = "ч.")
    data object MinuteShort: StringItem(valueUz = "daq.", valueEn = "min", valueUzCyr = "daq.", valueRu = "мин.")
    data object SecondShort: StringItem(valueUz = "sek.", valueEn = "sec", valueUzCyr = "сек.", valueRu = "сек.")
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
    data object Monday: StringItem(valueUz = "Dushanba", valueEn = "Monday", valueUzCyr = "Душанба", valueRu = "Понедельник")
    data object Tuesday: StringItem(valueUz = "Seshanba", valueEn = "Tuesday", valueUzCyr = "Сешанба", valueRu = "Вторник")
    data object Wednesday: StringItem(valueUz = "Chorshanba", valueEn = "Wednesday", valueUzCyr = "Чоршанба", valueRu = "Среда")
    data object Thursday: StringItem(valueUz = "Payshanba", valueEn = "Thursday", valueUzCyr = "Пайшанба", valueRu = "Четверг")
    data object Friday: StringItem(valueUz = "Juma", valueEn = "Friday", valueUzCyr = "Жума", valueRu = "Пятница")
    data object Saturday: StringItem(valueUz = "Shanba", valueEn = "Saturday", valueUzCyr = "Шанба", valueRu = "Суббота")
    data object Sunday: StringItem(valueUz = "Yakshanba", valueEn = "Sunday", valueUzCyr = "Якшанба", valueRu = "Воскресенье")
    data object UnknownErrorOccurred: StringItem(valueUz = "Noma'lum xato yuz berdi", valueEn = "Unknown error occurred", valueUzCyr = "Номаълум хатолик содир бўлди", valueRu = "Произошла неизвестная ошибка")
    data object Unknown: StringItem(valueUz = "Noma'lum", valueEn = "Unknown", valueUzCyr = "Номаълум", valueRu = "Неизвестный")
    data object Total: StringItem(valueUz = "Jami", valueEn = "Total", valueUzCyr = "Жами", valueRu = "Итого")
    data object FullName: StringItem(valueUz = "F.I.SH", valueEn = "Full name", valueUzCyr = "Ф.И.Ш", valueRu = "И.Ф.О")
    data object PhoneNumber: StringItem(valueUz = "Telefon raqami", valueEn = "Phone number", valueUzCyr = "Телефон рақами", valueRu = "Номер телефона")
    data object Region: StringItem(valueUz = "Viloyat", valueEn = "Region", valueUzCyr = "Viloyat", valueRu = "Область")
    data object Payment: StringItem(valueUz = "To'lov", valueEn = "Payment", valueUzCyr = "Тўлов", valueRu = "Оплата")
    data object CallViaPhone: StringItem(valueUz = "Telefon orqali murojaat qilish", valueEn = "Call via phone", valueUzCyr = "Телефон орқали мурожаат қилиш", valueRu = "Звонить по телефону")
    data object ContactViaTelegram: StringItem(valueUz = "Telegram orqali bog'lanish", valueEn = "Contact via Telegram", valueUzCyr = "Телеграм орқали боғланиш", valueRu = "Связаться через Telegram")
    data object ContactWithUs: StringItem(valueUz = "Biz bilan bog'lanish", valueEn = "Contact with us", valueUzCyr = "Биз билан боғланиш", valueRu = "Связаться с нами")
    data object YouAreNotLoggedIn: StringItem(valueUz = "Siz hali tizimga kirmagansiz", valueEn = "You are not logged in yet", valueUzCyr = "Сиз ҳали тизимга кирмагансиз", valueRu = "Вы еще не вошли в систему")
    data object LoginToContinue: StringItem(valueUz = "Davom etish uchun tizimga kiring", valueEn = "Log in to continue", valueUzCyr = "Давом этиш учун тизимга киринг", valueRu = "Войдите для продолжения")
    data object Login: StringItem(valueUz = "Kirish", valueEn = "Login", valueUzCyr = "Кириш", valueRu = "Войти")
    data object LogOut: StringItem(valueUz = "Chiqish", valueEn = "Log out", valueUzCyr = "Чиқиш", valueRu = "Выйти")
    data object Language_: StringItem(valueUz = "Til", valueEn = "Language", valueUzCyr = "Тил", valueRu = "Язык")
    data object AboutApp: StringItem(valueUz = "Ilova haqida", valueEn = "About app", valueUzCyr = "Илова ҳақида", valueRu = "О приложении")
    data object TermsAndConditions: StringItem(valueUz = "Oferta va foydalanish shartlari", valueEn = "Terms and conditions", valueUzCyr = "Оферта ва фойдаланиш шартлари", valueRu = "Условия и положения")
    data object EnterYourPhoneNumberToLogIn: StringItem(valueUz = "Kirish uchun telefon raqamingizni kiriting", valueEn = "Enter your phone number to login", valueUzCyr = "Кириш учун телефон рақамингизни киритинг", valueRu = "Введите свой номер телефона для входа")
    data object ByAuthorisationYouAgreeText: StringItem(valueUz = "Avtorizatsiyadan o'tish orqali siz", valueEn = "By authorizing,", valueUzCyr = "Автозатсиядан ўтиш орқали сиз", valueRu = "Авторизуясь, вы")
    data object YouWillAgreeText: StringItem(valueUz = "Oferta va foydalanish shartlariga o'z roziligingizni bildirasiz", valueEn = "you agree to the Terms and conditions", valueUzCyr = "Оферта ва фойдаланиш шартларига ўз розилигингизни билдирисиз", valueRu = "вы соглашаетесь с Условиями и положениями")
    data object Settings: StringItem(valueUz = "Sozlamalar", valueEn = "Settings", valueUzCyr = "Созламалар", valueRu = "Настройки")
    data object EditProfile: StringItem(valueUz = "Profilni tahrirlash", valueEn = "Edit profile", valueUzCyr = "Профилни таҳрирлаш", valueRu = "Редактировать профиль")
    data object FirstName: StringItem(valueUz = "Ism", valueEn = "First name", valueUzCyr = "Исм", valueRu = "Имя")
    data object MiddleName: StringItem(valueUz = "Sharif", valueEn = "Middle name", valueUzCyr = "Шариф", valueRu = "Отчество")
    data object LastName: StringItem(valueUz = "Familiya", valueEn = "Last name", valueUzCyr = "Фамилия", valueRu = "Фамилия")
    data object DeleteProfile: StringItem(valueUz = "Profilni o'chirish", valueEn = "Delete profile", valueUzCyr = "Профилни ўчириш", valueRu = "Удалить профиль")
    data object Save: StringItem(valueUz = "Saqlash", valueEn = "Save", valueUzCyr = "Сақлаш", valueRu = "Сохранить")
    data object Cancel: StringItem(valueUz = "Bekor qilish", valueEn = "Cancel", valueUzCyr = "Бекор қилиш", valueRu = "Отмена")
    data object Select: StringItem(valueUz = "Tanlash", valueEn = "Select", valueUzCyr = "Танлаш", valueRu = "Выбрать")
    data object Home: StringItem(valueUz = "Asosiy", valueEn = "Home", valueUzCyr = "Асосий", valueRu = "Главная")
    data object Location: StringItem(valueUz = "Joylashuv", valueEn = "Location", valueUzCyr = "Жойлашув", valueRu = "Местоположение")
    data object Profile: StringItem(valueUz = "Profil", valueEn = "Profile", valueUzCyr = "Профил", valueRu = "Профиль")
    data object OtpSentText1: StringItem(valueUz = "", valueEn = "SMS message has been sent to number ", valueUzCyr = "", valueRu = "SMS сообщение было отправлено на номер ")
    data object OtpSentText2: StringItem(valueUz = " raqamiga SMS xabar yuborildi. Bir martalik maxfiy kodni kiriting.", valueEn = ". Enter one-time code.", valueUzCyr = " ракамга SMS хабар юборилди. Бир мартага махфий кодни киритинг.", valueRu = ". Введите одноразовый код.")
    data object ResendIn: StringItem(valueUz = "Qayta jo'natish", valueEn = "Resend in", valueUzCyr = "Қайта юбориш", valueRu = "Отправить снова через")
    data object From: StringItem(valueUz = "Qayerdan", valueEn = "From", valueUzCyr = "Қайердан", valueRu = "Откуда")
    data object To: StringItem(valueUz = "Qayerga", valueEn = "To", valueUzCyr = "Қайерга", valueRu = "Куда")
    data object Tickets: StringItem(valueUz = "Biletlar", valueEn = "Tickets", valueUzCyr = "Билетлар", valueRu = "Билеты")
    data object AreYouSureYouWantToCancel: StringItem(valueUz = "Rostdan ham bekor qilmoqchimisiz?", valueEn = "Are you sure you want to cancel?", valueUzCyr = "Ҳақиқатдан ҳам бекор қилмоқчимисиз?", valueRu = "Вы действительно хотите отменить?")
    data object Stay: StringItem(valueUz = "Qolish", valueEn = "Stay", valueUzCyr = "Қолиш", valueRu = "Оставаться")
    data object Exit: StringItem(valueUz = "Chiqish", valueEn = "Exit", valueUzCyr = "Чиқиш", valueRu = "Выход")
    data object CardInfo: StringItem(valueUz = "Karta ma'lumotlari", valueEn = "Card info", valueUzCyr = "Карта маълумотлари", valueRu = "Информация о карте")
    data object CardNumber: StringItem(valueUz = "Karta raqami", valueEn = "Card number", valueUzCyr = "Карта рақами", valueRu = "Номер карты")
    data object ValidDate: StringItem(valueUz = "Amal qilish muddati", valueEn = "Valid date", valueUzCyr = "Амал қилиш муддати", valueRu = "Срок действия")
    data object MMYY: StringItem(valueUz = "OO/YY", valueEn = "MM/YY", valueUzCyr = "ОО/YY", valueRu = "ММ/ГГ")
    data object TimerEndedPleaseTry: StringItem(valueUz = "Vaqt tugadi. Iltimos qaytadan urinib ko'ring", valueEn = "Timer ended. Please try again from beginning", valueUzCyr = "Вақт тугади. Илтимос қайтадан уриниб кўринг", valueRu = "Время вышло. Пожалуйста, попробуйте снова с самого начала")
    data object SaveCardInfoToLaterUse: StringItem(valueUz = "Keyinchalik ishlatish uchun eslab qolish", valueEn = "Save card info to later use", valueUzCyr = "Кейинчалик ишлатиш учун эслаб қолиш", valueRu = "Сохранить информацию о карте для дальнейшего использования")
    data object Ticket: StringItem(valueUz = "bilet", valueEn = "ticket", valueUzCyr = "билет", valueRu = "билет")
    data object Sum: StringItem(valueUz = "so‘m", valueEn = "sum", valueUzCyr = "сўм", valueRu = "сумма")
    data object Additional: StringItem(valueUz = "Qo'shimcha", valueEn = "Additional", valueUzCyr = "Қўшимча", valueRu = "Дополнительно")
    data object SendSMS: StringItem(valueUz = "SMS yuborish", valueEn = "Send SMS", valueUzCyr = "SMS юбориш", valueRu = "Отправить SMS")
    data object RoundTrip: StringItem(valueUz = "Borish-kelish", valueEn = "Round trip", valueUzCyr = "Бориш-келиш", valueRu = "Туда и обратно")
    data object OneWay: StringItem(valueUz = "Bir tomonga", valueEn = "One-way", valueUzCyr = "Бир томонга", valueRu = "В одну сторону")
    data object Departure: StringItem(valueUz = "Jo'nash", valueEn = "Departure", valueUzCyr = "Жўнаш", valueRu = "Отправление")
    data object Return: StringItem(valueUz = "Qaytish", valueEn = "Return", valueUzCyr = "Қайтиш", valueRu = "Возвращение")
    data object SearchJourneys: StringItem(valueUz = "Sayohatlarni qidirish", valueEn = "Search journeys", valueUzCyr = "Сайохатларни қидириш", valueRu = "Поиск поездок")
    data object FeatureComingSoon: StringItem(valueUz = "Ushbu funktsiya tez orada mavjud bo'ladi", valueEn = "This feature will be available soon", valueUzCyr = "Ушбу функция тез орада мавжуд бўлади", valueRu = "Эта функция будет доступна скоро")
    data object FillAllFields: StringItem(valueUz = "Barcha maydonlarni to'ldiring", valueEn = "Fill all the fields", valueUzCyr = "Барча майдонларни тўлдиринг", valueRu = "Заполните все поля")
    data object ErrorFetchingRegions: StringItem(valueUz = "Viloyatlarni olishda xatolik yuz berdi", valueEn = "Error while fetching regions", valueUzCyr = "Вилоятларни олишда хатолик юз берди", valueRu = "Ошибка при получении регионов")
    data object ErrorFetchingPosts: StringItem(valueUz = "Xabarlarni olishda xatolik yuz berdi", valueEn = "Error while fetching posts", valueUzCyr = "Хабарларни олишда хатолик юз берди", valueRu = "Ошибка при получении сообщений")
    data object Loading: StringItem(valueUz = "Yuklanmoqda...", valueEn = "Loading...", valueUzCyr = "Юкланмоқда...", valueRu = "Загрузка...")
    data object NoPermission: StringItem(valueUz = "Ruxsat yo'q", valueEn = "No permission", valueUzCyr = "Рўхсат йўқ", valueRu = "Нет разрешения")
    data object TurnOnGPS: StringItem(valueUz = "GPSni yoqing", valueEn = "Turn on GPS", valueUzCyr = "GPSни ёқинг", valueRu = "Включите GPS")
    data object ErrorFetchingLocation: StringItem(valueUz = "Manzilni olishda xatolik yuz berdi", valueEn = "Error while fetching location", valueUzCyr = "Манзилни олишда хатолик юз берди", valueRu = "Ошибка при получении местоположения")
    data object ErrorSendingOTP: StringItem(valueUz = "OTP yuborishda xatolik yuz berdi", valueEn = "Error while sending OTP", valueUzCyr = "OTP юборишда хатолик юз берди", valueRu = "Ошибка при отправке OTP")
    data object InvalidPhoneNumber: StringItem(valueUz = "Noto'g'ri telefon raqami", valueEn = "Invalid phone number", valueUzCyr = "Нотоғри телефон рақами", valueRu = "Неверный номер телефона")
    data object InvalidOTP: StringItem(valueUz = "Noto‘g‘ri OTP", valueEn = "Invalid OTP", valueUzCyr = "Нотоғри OTP", valueRu = "Неверный OTP")
    data object PassengerInfo: StringItem(valueUz = "Yo'lovchi ma'lumotlari", valueEn = "Passenger Info", valueUzCyr = "Йўловчи маълумотлари", valueRu = "Информация о пассажире")
    data object Passenger: StringItem(valueUz = "Yo'lovchi", valueEn = "Passenger", valueUzCyr = "Йўловчи", valueRu = "Пассажир")
    data object Seat: StringItem(valueUz = "O'rin", valueEn = "Seat", valueUzCyr = "Ўрин", valueRu = "Место")
    data object InvalidImageSize: StringItem(valueUz = "Noto‘g‘ri rasm o‘lchami", valueEn = "Invalid image size", valueUzCyr = "Нотоғри расм ўлчами", valueRu = "Неверный размер изображения")
    data object ErrorLoadingProfile: StringItem(valueUz = "Profilni yuklashda xatolik yuz berdi", valueEn = "Error while loading profile", valueUzCyr = "Профилни юклашда хатолик юз берди", valueRu = "Ошибка при загрузке профиля")
    data object NonStop: StringItem(valueUz = "To‘xtovsiz", valueEn = "Non-stop", valueUzCyr = "Тўхтовсиз", valueRu = "Без остановок")
    data object StopAt: StringItem(valueUz = "To‘xtash", valueEn = "Stop at", valueUzCyr = "Тўхташ", valueRu = "Остановка")
    data object Remaining: StringItem(valueUz = "Mavjud", valueEn = "Remaining", valueUzCyr = "Мавжуд", valueRu = "Доступно")
    data object JourneyResults: StringItem(valueUz = "Sayohat natijalari", valueEn = "Journey Results", valueUzCyr = "Сайохат натижалари", valueRu = "Результаты поездки")
    data object JourneysFound: StringItem(valueUz = "Sayohat(lar) topildi", valueEn = "Journey(s) found", valueUzCyr = "Сайохат(лар) топилди", valueRu = "Поездка(и) найдены")
    data object NoResultsFound: StringItem(valueUz = "Natija topilmadi :( Ehtimol, boshqa sanani sinab ko'rishingiz mumkin?", valueEn = "No Results Found :( Maybe you can try another date?", valueUzCyr = "Натижа топилмади :( Эҳтимол, бошқа санани синаб кўришингиз мумкин?", valueRu = "Результаты не найдены :( Может, вы попробуете другую дату?")
    data object SelectAnotherDate: StringItem(valueUz = "Boshqa sanani tanlash", valueEn = "Select Another Date", valueUzCyr = "Бошқа санани танлаш", valueRu = "Выбрать другую дату")
    data object NoSeatsAvailable: StringItem(valueUz = "Mavjud o'rindiqlar yo'q", valueEn = "No seats available", valueUzCyr = "Мавжуд ўриндиқлар йўқ", valueRu = "Нет доступных мест")
    data object Available: StringItem(valueUz = "Mavjud", valueEn = "Available", valueUzCyr = "Мавжуд", valueRu = "Доступно")
    data object Selected: StringItem(valueUz = "Tanlangan", valueEn = "Selected", valueUzCyr = "Танланган", valueRu = "Выбран")
    data object Reserve: StringItem(valueUz = "Zaxira", valueEn = "Reserve", valueUzCyr = "Захира", valueRu = "Резерв")
    data object Unavailable: StringItem(valueUz = "Mavjud emas", valueEn = "Unavailable", valueUzCyr = "Мавжуд эмас", valueRu = "Недоступно")
    data object SelectSeats: StringItem(valueUz = "O'rinlarni tanlang", valueEn = "Select Seats", valueUzCyr = "Ўринларни танланг", valueRu = "Выберите места")
    data object Continue: StringItem(valueUz = "Davom etish", valueEn = "Continue", valueUzCyr = "Давом этиш", valueRu = "Продолжить")
    data object Start: StringItem(valueUz = "Boshlash", valueEn = "Start", valueUzCyr = "Бошлаш", valueRu = "Начать")
    data object SearchForTicketsToDestination: StringItem(valueUz = "Manzilingiz Sari Chiptalarni Qidiring", valueEn = "Search For Tickets To your Destination", valueUzCyr = "Манзилингиз Сари Чипталарни Қидиринг", valueRu = "Ищите Билеты в Ваше Место Назначения")
    data object DepartureTime: StringItem(valueUz = "Jo'nash vaqti", valueEn = "Departure Time", valueUzCyr = "Жўнаш вақти", valueRu = "Время отправления")
    data object PurchaseTime: StringItem(valueUz = "Sotib olingan vaqt", valueEn = "Purchase Time", valueUzCyr = "Сотиб олинган вақт", valueRu = "Время покупки")
    data object TicketPrice: StringItem(valueUz = "Chipta narxi", valueEn = "Ticket Price", valueUzCyr = "Чипта нархи", valueRu = "Цена билета")
    data object YourTickets: StringItem(valueUz = "Sizning chiptalaringiz", valueEn = "Your Tickets", valueUzCyr = "Сизнинг чипталариңиз", valueRu = "Ваши билеты")
    data object ShowOldTickets: StringItem(valueUz = "Eski chiptalarni ko'rsatish", valueEn = "Show old tickets", valueUzCyr = "Эски чипталарни кўрсатиш", valueRu = "Показать старые билеты")
    data object WaitingForJourney: StringItem(valueUz = "Sayohatni kutyapti", valueEn = "Waiting for journey", valueUzCyr = "Сайохатни кутяпти", valueRu = "Ожидает поездку")
    data object NoTicketsFoundWaiting: StringItem(valueUz = "Sayohatni kutayotgan hech qanday chipta topilmadi :(", valueEn = "There are no ticket found that are waiting for journey :(", valueUzCyr = "Сайохатни кутяптиган ҳеч қандай чипта топилмади :(", valueRu = "Нет билетов, ожидающих поездку :(")
    data object BuyTicket: StringItem(valueUz = "Chipta sotib olish", valueEn = "Buy a ticket", valueUzCyr = "Чипта сотиб олиш", valueRu = "Купить билет")
    data object OldJourneys: StringItem(valueUz = "Eski sayohatlar", valueEn = "Old journeys", valueUzCyr = "Эски сайохатлар", valueRu = "Старые поездки")
    data object NoOldJourneys: StringItem(valueUz = "Hali eski sayohatlaringiz yo'q.", valueEn = "You have not any old journeys yet.", valueUzCyr = "Ҳали эски сайохатларингиз йўқ.", valueRu = "У вас еще нет старых поездок.")
}