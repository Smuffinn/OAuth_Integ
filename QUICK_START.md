# 🚀 Quick Start Guide

Follow these steps to get your OAuth2 application running in minutes!

## Prerequisites
- ✅ Java 11 installed
- ✅ Maven installed
- ✅ Internet connection

---

## Step 1: Set Up OAuth2 Providers (One-Time Setup)

### Google OAuth2
1. Go to [Google Cloud Console](https://console.cloud.google.com/)
2. Create a new project
3. Navigate to **APIs & Services > Credentials**
4. Click **Create Credentials > OAuth client ID**
5. Select **Web application**
6. Add authorized redirect URI: `http://localhost:8080/login/oauth2/code/google`
7. **Save your Client ID and Client Secret**

### GitHub OAuth2
1. Go to [GitHub Developer Settings](https://github.com/settings/developers)
2. Click **New OAuth App**
3. Fill in:
   - Homepage URL: `http://localhost:8080`
   - Authorization callback URL: `http://localhost:8080/login/oauth2/code/github`
4. **Save your Client ID and generate Client Secret**

📖 **Need detailed instructions?** See [OAUTH_SETUP_GUIDE.md](OAUTH_SETUP_GUIDE.md)

---

## Step 2: Configure Your Credentials

### Option A: Using PowerShell Script (Recommended for Windows)
```powershell
# Run the setup script
.\set-env.ps1
```

The script will:
- Prompt you to enter your OAuth2 credentials
- Set them as environment variables
- Optionally save them to a `.env` file

### Option B: Manual Configuration
1. Copy `.env.example` to `.env`
2. Edit `.env` and add your credentials:
```env
GOOGLE_CLIENT_ID=your-google-client-id
GOOGLE_CLIENT_SECRET=your-google-client-secret
GITHUB_CLIENT_ID=your-github-client-id
GITHUB_CLIENT_SECRET=your-github-client-secret
```

3. Load the environment variables:
```powershell
Get-Content .env | ForEach-Object { 
    if ($_ -match '^([^=]+)=(.*)$') { 
        [Environment]::SetEnvironmentVariable($matches[1], $matches[2], 'Process') 
    } 
}
```

---

## Step 3: Build and Run

### Build the project
```powershell
mvn clean install
```

### Run the application
```powershell
mvn spring-boot:run
```

---

## Step 4: Test Your Application

1. Open your browser and go to: **http://localhost:8080**
2. You should see a login page with OAuth2 options
3. Click **"Login with Google"** or **"Login with GitHub"**
4. Authorize the application
5. You should be redirected to your profile page

---

## 🎯 Quick Commands

```powershell
# Set environment variables (run first!)
.\set-env.ps1

# Build and run in one command
mvn clean spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring.profiles.active=local

# View H2 console (for debugging)
# http://localhost:8080/h2-console
```

---

## 🐛 Troubleshooting

### "redirect_uri_mismatch" error
- ✓ Verify redirect URIs match exactly in Google/GitHub settings
- ✓ No trailing slashes
- ✓ Use `http://` not `https://` for localhost

### "invalid_client" error
- ✓ Check your Client ID and Secret are correct
- ✓ No extra spaces in credentials
- ✓ Environment variables are set

### Application won't start
- ✓ Run `.\set-env.ps1` to set environment variables
- ✓ Check port 8080 is not in use
- ✓ Run `mvn clean install` first

### Still having issues?
- Check [OAUTH_SETUP_GUIDE.md](OAUTH_SETUP_GUIDE.md) for detailed troubleshooting
- Check application logs in the console

---

## 📁 Project Structure

```
OAuth/
├── src/main/java/com/example/oauth2demo/
│   ├── config/              # Security configuration
│   ├── controller/          # REST endpoints
│   ├── model/               # User & AuthProvider entities
│   ├── repository/          # Database access
│   ├── security/            # Custom OAuth2 user service
│   └── service/             # Business logic
├── src/main/resources/
│   └── application.properties  # Application configuration
├── .env.example             # Template for credentials
├── set-env.ps1              # PowerShell setup script
├── QUICK_START.md           # This file
└── OAUTH_SETUP_GUIDE.md     # Detailed setup guide
```

---

## 🔒 Security Notes

- ⚠️ **NEVER** commit `.env` file to git (it's in `.gitignore`)
- ⚠️ **NEVER** commit real credentials to version control
- ✓ Use environment variables for production
- ✓ Rotate secrets regularly
- ✓ Use HTTPS in production

---

## 🎉 Next Steps

After successful login:
- View your profile at `/profile`
- Check user data is being saved (H2 console: `/h2-console`)
- Customize the UI in the frontend
- Add more OAuth2 providers (Facebook, Twitter, etc.)

---

## 📚 Additional Resources

- [Spring Security OAuth2 Documentation](https://spring.io/guides/tutorials/spring-boot-oauth2/)
- [Google OAuth2 Documentation](https://developers.google.com/identity/protocols/oauth2)
- [GitHub OAuth Documentation](https://docs.github.com/en/developers/apps/building-oauth-apps)

---

**Happy Coding! 🚀**
