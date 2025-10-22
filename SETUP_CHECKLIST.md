# ✅ OAuth2 Setup Checklist

Print this checklist and check off items as you complete them!

---

## 📋 Part 1: Google OAuth2 Setup

### A. Create Google Cloud Project
- [ ] Go to [Google Cloud Console](https://console.cloud.google.com/)
- [ ] Sign in with Google account
- [ ] Click project dropdown → "New Project"
- [ ] Enter project name: `_____________________`
- [ ] Click "Create"
- [ ] Select the new project

### B. Configure OAuth Consent Screen
- [ ] Go to "APIs & Services" → "Credentials"
- [ ] Click "Configure Consent Screen"
- [ ] Select "External" user type
- [ ] Fill in required fields:
  - [ ] App name: `_____________________`
  - [ ] User support email: `_____________________`
  - [ ] Developer contact: `_____________________`
- [ ] Click "Save and Continue" through all steps

### C. Create OAuth Client ID
- [ ] Go to "Credentials" tab
- [ ] Click "Create Credentials" → "OAuth client ID"
- [ ] Select "Web application"
- [ ] Name: `_____________________`
- [ ] Add authorized redirect URI:
  ```
  http://localhost:8080/login/oauth2/code/google
  ```
- [ ] Click "Create"

### D. Save Credentials
```
Google Client ID:     _________________________________
Google Client Secret: _________________________________
```

---

## 📋 Part 2: GitHub OAuth2 Setup

### A. Create OAuth App
- [ ] Go to [GitHub Developer Settings](https://github.com/settings/developers)
- [ ] Sign in to GitHub
- [ ] Click "OAuth Apps" (left sidebar)
- [ ] Click "New OAuth App"

### B. Configure OAuth App
- [ ] Application name: `_____________________`
- [ ] Homepage URL:
  ```
  http://localhost:8080
  ```
- [ ] Authorization callback URL:
  ```
  http://localhost:8080/login/oauth2/code/github
  ```
- [ ] Click "Register application"

### C. Generate Client Secret
- [ ] Note down Client ID
- [ ] Click "Generate a new client secret"
- [ ] Copy Client Secret immediately

### D. Save Credentials
```
GitHub Client ID:     _________________________________
GitHub Client Secret: _________________________________
```

---

## 📋 Part 3: Configure Application

### A. Set Up Environment Variables
- [ ] Open PowerShell in project directory
- [ ] Run: `.\set-env.ps1`
- [ ] Enter Google Client ID
- [ ] Enter Google Client Secret
- [ ] Enter GitHub Client ID
- [ ] Enter GitHub Client Secret
- [ ] Choose to save to `.env` file (recommended)

### B. Verify Configuration
- [ ] Check `.env` file exists (if you chose to save)
- [ ] Confirm `.env` is listed in `.gitignore`
- [ ] Verify `application.properties` has environment variable placeholders

---

## 📋 Part 4: Build and Run

### A. Build Application
```powershell
mvn clean install
```
- [ ] Command executed successfully
- [ ] No build errors
- [ ] Dependencies downloaded

### B. Run Application
```powershell
mvn spring-boot:run
```
- [ ] Application starts without errors
- [ ] Server running on port 8080
- [ ] No configuration errors in logs

---

## 📋 Part 5: Testing

### A. Test Google OAuth2
- [ ] Open browser: `http://localhost:8080`
- [ ] Click "Login with Google"
- [ ] Select Google account
- [ ] Grant permissions
- [ ] Redirected to profile page
- [ ] User info displays correctly

### B. Test GitHub OAuth2
- [ ] Go back to: `http://localhost:8080`
- [ ] Logout (if needed)
- [ ] Click "Login with GitHub"
- [ ] Authorize application
- [ ] Redirected to profile page
- [ ] User info displays correctly

### C. Test Logout
- [ ] Click logout button
- [ ] Redirected to home page
- [ ] Not logged in anymore

---

## 📋 Part 6: Security Verification

### A. Check Git Configuration
- [ ] `.env` file is in `.gitignore`
- [ ] No credentials in `application.properties` (only env vars)
- [ ] No `.env` file in git status

### B. Verify Security Settings
- [ ] HTTPS enabled (for production)
- [ ] CSRF protection enabled
- [ ] Secure cookies configured
- [ ] Session management working

---

## 🎯 Quick Reference

### Important URLs
```
Application Home:    http://localhost:8080
User Profile:        http://localhost:8080/profile
H2 Console:          http://localhost:8080/h2-console
Google Cloud:        https://console.cloud.google.com/
GitHub Settings:     https://github.com/settings/developers
```

### Important Files
```
Configuration:       src/main/resources/application.properties
Environment:         .env
Setup Script:        set-env.ps1
Security Config:     src/main/java/.../config/SecurityConfig.java
```

### Common Commands
```powershell
# Set environment variables
.\set-env.ps1

# Build
mvn clean install

# Run
mvn spring-boot:run

# Clean build
mvn clean package
```

---

## ⚠️ Troubleshooting Checklist

If something doesn't work, check:

- [ ] Environment variables are set (run `set-env.ps1`)
- [ ] Redirect URIs match exactly in OAuth apps
- [ ] Port 8080 is not in use
- [ ] Client ID and Secret are correct
- [ ] No extra spaces in credentials
- [ ] Browser cookies are enabled
- [ ] Internet connection is working
- [ ] Maven dependencies downloaded successfully

---

## 📝 Notes

Use this section to write down any custom configurations or notes:

```
_____________________________________________________________

_____________________________________________________________

_____________________________________________________________

_____________________________________________________________

_____________________________________________________________
```

---

**✅ Setup Complete!**

Date: _______________  
Completed by: _______________

---

## 🎉 What's Next?

After successful setup:
- [ ] Customize the UI
- [ ] Add more user profile fields
- [ ] Implement additional features
- [ ] Deploy to production
- [ ] Set up production OAuth redirect URIs

---

**Need help?** Check:
- `QUICK_START.md` - Quick reference guide
- `OAUTH_SETUP_GUIDE.md` - Detailed setup instructions
- `README.md` - Project documentation
