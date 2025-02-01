import UIKit
import SwiftUI
import ComposeApp

struct ComposeView: UIViewControllerRepresentable {
    @Binding var theme: String
    
    func makeUIViewController(context: Context) -> UIViewController {
        MainViewControllerKt.MainViewController(themeCallback: { theme in
            self.theme = theme // Update the theme with the received String value
        })
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}
}

struct ContentView: View {
    @State private var theme: String = "dark" // Default to dark theme as a string
    
    var body: some View {
        ComposeView(theme: $theme)
            .ignoresSafeArea(edges: .all) // TO REMOVE STATUS BAR
            .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
            .preferredColorScheme(theme == "dark" ? .dark : .light) // Dynamically change based on theme
    }
}





