import SwiftUI
import ComposeApp

@main
struct iOSApp: App {

    init() {
        LoggerKt.doInitLogger()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}