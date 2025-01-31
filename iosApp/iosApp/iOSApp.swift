import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseMessaging

// class AppDelegate: NSObject, UIApplicationDelegate {
    
//     func application(_ app: UIApplication,
//                      open url: URL,
//                      options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
//
//         if url.scheme == "ticketatorapp" {
//             let queryItems = URLComponents(url: url, resolvingAgainstBaseURL: false)?.queryItems
//             let sum = queryItems?.first(where: { $0.name == "sum" })?.value ?? "0"
//
//             print("BROADCASTTT: Received Result from Ecommerce App: \(sum)")
//         }
//         return true
//     }
//
//

  //func application(_ application: UIApplication,
    //               didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {

 //     FirebaseApp.configure() //important
      
      //By default showPushNotification value is true.
      //When set showPushNotification to false foreground push  notification will not be shown.
      //You can still get notification content using #onPushNotification listener method.
 //     NotifierManager.shared.initialize(configuration: NotificationPlatformConfigurationIos(
  //          showPushNotification: true,
 //           askNotificationPermissionOnStart: true,
 //           notificationSoundName: "")
 //     )
 //
 //   return true
 // }

//  func application(_ application: UIApplication, didRegisterForRemoteNotificationsWithDeviceToken deviceToken: Data) {
//        Messaging.messaging().apnsToken = deviceToken
//      print("Device token: \(deviceToken)")
//  }

// }

@main
struct iOSApp: App {
//     @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    var body: some Scene {
        WindowGroup {
            
            ContentView()           .onOpenURL{ url in
                print("URL 1: \(url)")
       
            }
    }
}
}

func checkDeepLink(url: URL) -> Int? {
guard let components = URLComponents(url: url, resolvingAgainstBaseURL: true) else {
    return nil
}

if let host = components.host {
    print("HOST: \(host)")
}

let queryItems = components.queryItems
let num1 = Int(queryItems?.first(where: { $0.name == "num1" })?.value ?? "0") ?? 0
let num2 = Int(queryItems?.first(where: { $0.name == "num2" })?.value ?? "0") ?? 0

let result = num1 + num2


return result
}
