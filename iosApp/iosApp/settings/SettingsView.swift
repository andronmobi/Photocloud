import SwiftUI
import sharedClient

struct SettingsView: View {
    
    private let settings: Settings

    init(_ settings: Settings) {
        self.settings = settings
    }

    var body: some View {
        VStack {
            Button(action: {
                settings.logout()
            }) {
                Text("Logout").frame(minWidth: 0, maxWidth: .infinity)
            }
            .buttonStyle(.bordered)
            .background(Color(red: 98 / 255, green: 0 / 255, blue: 242 / 255))
            .foregroundColor(.white)
            .cornerRadius(4)
            .padding(.horizontal, 60)
            .padding(.bottom, 32)
        }.frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .bottomLeading
        )
    }
}
