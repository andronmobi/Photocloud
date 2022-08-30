import SwiftUI
import sharedClient

struct LoginView: View {

    private let login: Login

    @ObservedObject
    private var state: ObservableValue<LoginState>

    @State
    private var name: String = ""
    @State
    private var password: String = ""
    @State
    private var host: String = ""

    init(_ login: Login) {
        self.login = login
        state = ObservableValue(login.state)
    }

    var body: some View {
        ZStack(alignment: .top) {
            let loginState = state.value

            switch loginState {
            case let errorState as LoginState.Error:
                // Snackbar
                HStack {
                    Text(errorState.message)
                        .padding(.horizontal, 10)
                        .foregroundColor(Color.white)
                        .font(.body)
                    Spacer()
                    Button(action: {
                        login.onSnackbarClose()
                    }) {
                        Text("OK")
                            .foregroundColor(Color.white)
                            .font(.body)
                            .padding(.vertical, 10)
                            .padding(.horizontal, 15)
                            .background(Color(red: 98 / 255, green: 0 / 255, blue: 242 / 255))
                            .cornerRadius(4)
                    }.padding(.vertical, 10)
                    Spacer().frame(width: 10)
                }.background(Color.black)
                    .frame(maxWidth: .infinity, alignment: .leading)
                    .zIndex(1)
                    .cornerRadius(4)
                    .padding(10)

            case loginState as LoginState.Loading:
                Text("Loading")

            default:
                EmptyView()
            }

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

                TextField("Host name or IP address", text: $host)
                    .autocapitalization(.none)
                    .textFieldStyle(.roundedBorder)
                    .padding(.horizontal, 60)
                    .padding(.top, 20)
                    .onAppear {
                        self.host = login.defaultHost
                    }

                Button(action: {
                    login.login(name: name, password: password, host: host)
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
            }.frame(
                maxWidth: .infinity,
                maxHeight: .infinity,
                alignment: .center
            )

        }.frame(
            maxWidth: .infinity,
            maxHeight: .infinity,
            alignment: .topLeading
        )

    }
}
