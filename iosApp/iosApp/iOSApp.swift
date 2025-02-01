import SwiftUI
import ComposeApp
import FirebaseCore
import FirebaseMessaging


// @UIApplicationMain
// class AppDelegate: NSObject, UIApplicationDelegate {
//     var window: UIWindow?
//     let rinku = RinkuIos.init(deepLinkFilter: nil, deepLinkMapper: nil)
//
//     func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey : Any]? = nil) -> Bool {
//         self.window = UIWindow(frame: UIScreen.main.bounds)
//         let mainViewController = UIHostingController(rootView: ContentView())
//         self.window!.rootViewController = mainViewController
//         self.window!.makeKeyAndVisible()
//
//         return true
//     }
//
//     func application(_ app: UIApplication, open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]) -> Bool {
//         rinku.onDeepLinkReceived(url: url.absoluteString)
//         return true
//     }
//
//     func application(_ application: UIApplication, continue userActivity: NSUserActivity, restorationHandler: @escaping ([UIUserActivityRestoring]?) -> Void) -> Bool {
//         rinku.onDeepLinkReceived(userActivity: userActivity)
//         return true
//     }
// }

@main
    struct iOSApp: App {
    var body: some Scene {
            let rinku = RinkuIos.init(deepLinkFilter: nil, deepLinkMapper: nil)
        WindowGroup {
            ContentView()
                .onOpenURL{ url in
                    print("URL 1: \(url)")
                    rinku.onDeepLinkReceived(url: url.absoluteString)
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
