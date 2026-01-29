import SwiftUI

@main
struct iOSApp: App {
    init() {
        MainViewControllerKt.initKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}