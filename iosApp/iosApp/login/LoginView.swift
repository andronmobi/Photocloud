import SwiftUI
import sharedClient

struct LoginView: View {

    private let login: Login

    init(login: Login) {
        self.login = login
    }

    var body: some View {
        VStack {
            Text("Login Screen")
            Button("Login", action: { login.login(name: "name", password: "password")})
        }
    }
}
