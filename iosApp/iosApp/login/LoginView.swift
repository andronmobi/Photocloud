import SwiftUI
import sharedClient

struct LoginView: View {

    private let login: Login

    @State
    private var name: String = ""
    @State
    private var password: String = ""

    init(_ login: Login) {
        self.login = login
    }

    var body: some View {
        VStack {
            Text("Please login").font(.title3)

            TextField("User name", text: $name)
                .autocapitalization(.none)
                .textFieldStyle(.roundedBorder)
                .padding(.horizontal, 60)
                .padding(.top, 40)

            SecureField("Password", text: $password)
                .autocapitalization(.none)
                .textFieldStyle(.roundedBorder)
                .padding(.horizontal, 60)
                .padding(.top, 20)

            Button(action: {
                if (!name.isEmpty && !password.isEmpty) {
                    login.login(name: name, password: password)
                } else {
                    // TODO
                }
            }) {
                Text("Login")
                    .frame(minWidth: 0, maxWidth: .infinity)
            }
                .buttonStyle(.bordered)
                .background(Color(red: 98 / 255, green: 0 / 255, blue: 242 / 255))
                .foregroundColor(.white)
                .cornerRadius(4)
                .padding(.horizontal, 60)
                .padding(.top, 20)
        }
    }
}
