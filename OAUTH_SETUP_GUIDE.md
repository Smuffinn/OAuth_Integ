# OAuth2 Setup Guide

This guide will walk you through setting up Google OAuth2 and GitHub OAuth2 for your Spring Boot application.

---

## 1. Set Up Google OAuth2

### Step 1: Go to Google Cloud Console
1. Visit [Google Cloud Console](https://console.cloud.google.com/)
2. Sign in with your Google account

### Step 2: Create a New Project
1. Click on the project dropdown at the top of the page
2. Click **"New Project"**
3. Enter a project name (e.g., "OAuth2-Demo")
4. Click **"Create"**
5. Wait for the project to be created and select it

### Step 3: Enable Google+ API (Optional but Recommended)
1. In the left sidebar, go to **"APIs & Services"** > **"Library"**
2. Search for **"Google+ API"**
3. Click on it and click **"Enable"**

### Step 4: Navigate to Credentials
1. In the left sidebar, go to **"APIs & Services"** > **"Credentials"**

### Step 5: Create OAuth Client ID
1. Click **"Create Credentials"** button at the top
2. Select **"OAuth client ID"**
3. If prompted, click **"Configure Consent Screen"** first:
   - Select **"External"** user type
   - Click **"Create"**
   - Fill in required fields:
     - App name: Your App Name
     - User support email: your email
     - Developer contact information: your email
   - Click **"Save and Continue"**
   - Skip the Scopes section (click "Save and Continue")
   - Skip the Test users section (click "Save and Continue")
   - Click **"Back to Dashboard"**

### Step 6: Create OAuth Client ID (Continued)
1. Go back to **"Credentials"** tab
2. Click **"Create Credentials"** > **"OAuth client ID"**
3. Select **"Web application"** as the application type
4. Enter a name (e.g., "OAuth2 Web Client")

### Step 7: Add Authorized Redirect URIs
Add the following redirect URI:
```
http://localhost:8080/login/oauth2/code/google
```

### Step 8: Create and Save Credentials
1. Click **"Create"**
2. A dialog will appear with your **Client ID** and **Client Secret**
3. **IMPORTANT**: Copy both values immediately:
   - Client ID (looks like: `xxxxx.apps.googleusercontent.com`)
   - Client Secret (a random string)

---

## 2. Set Up GitHub OAuth2

### Step 1: Go to GitHub Developer Settings
1. Visit [GitHub Developer Settings](https://github.com/settings/developers)
2. Sign in to your GitHub account

### Step 2: Click "New OAuth App"
1. Click **"New OAuth App"** button
2. If you don't see it, click **"OAuth Apps"** in the left sidebar first

### Step 3: Fill in the Application Details
Fill in the following information:

- **Application name**: Your App Name (e.g., "OAuth2 Demo")
- **Homepage URL**: 
  ```
  http://localhost:8080
  ```
- **Application description**: (Optional) A brief description of your app
- **Authorization callback URL**: 
  ```
  http://localhost:8080/login/oauth2/code/github
  ```

### Step 4: Register Application
1. Click **"Register application"**

### Step 5: Generate Client Secret
1. After registration, you'll see your **Client ID**
2. Click **"Generate a new client secret"**
3. **IMPORTANT**: Copy the **Client Secret** immediately (you won't be able to see it again)
4. Note down both:
   - Client ID
   - Client Secret

---

## 3. Configure Your Application

### Step 1: Update application.properties
1. Open `src/main/resources/application.properties`
2. Replace the placeholder values with your actual credentials:

```properties
# OAuth2 Google
spring.security.oauth2.client.registration.google.client-id=YOUR_GOOGLE_CLIENT_ID
spring.security.oauth2.client.registration.google.client-secret=YOUR_GOOGLE_CLIENT_SECRET
spring.security.oauth2.client.registration.google.scope=email,profile

# OAuth2 GitHub
spring.security.oauth2.client.registration.github.client-id=YOUR_GITHUB_CLIENT_ID
spring.security.oauth2.client.registration.github.client-secret=YOUR_GITHUB_CLIENT_SECRET
spring.security.oauth2.client.registration.github.scope=user:email,read:user
```

### Step 2: Security Best Practices
**NEVER commit sensitive credentials to version control!**

#### Option A: Use Environment Variables (Recommended)
1. Update your `application.properties`:
```properties
# OAuth2 Google
spring.security.oauth2.client.registration.google.client-id=${GOOGLE_CLIENT_ID}
spring.security.oauth2.client.registration.google.client-secret=${GOOGLE_CLIENT_SECRET}
spring.security.oauth2.client.registration.google.scope=email,profile

# OAuth2 GitHub
spring.security.oauth2.client.registration.github.client-id=${GITHUB_CLIENT_ID}
spring.security.oauth2.client.registration.github.client-secret=${GITHUB_CLIENT_SECRET}
spring.security.oauth2.client.registration.github.scope=user:email,read:user
```

2. Set environment variables before running:
```bash
# Windows (PowerShell)
$env:GOOGLE_CLIENT_ID="your-google-client-id"
$env:GOOGLE_CLIENT_SECRET="your-google-client-secret"
$env:GITHUB_CLIENT_ID="your-github-client-id"
$env:GITHUB_CLIENT_SECRET="your-github-client-secret"

# Or create a .env file and load it
```

#### Option B: Use application-local.properties
1. Create `application-local.properties` in the same directory
2. Add it to `.gitignore`
3. Put your real credentials there
4. Run with: `mvn spring-boot:run -Dspring.profiles.active=local`

---

## 4. Run Your Application

### Step 1: Install Dependencies
```bash
mvn clean install
```

### Step 2: Run the Application
```bash
mvn spring-boot:run
```

### Step 3: Test OAuth2 Login
1. Open your browser and go to: `http://localhost:8080`
2. You should see login options for Google and GitHub
3. Click on either button to test the OAuth2 flow
4. After successful authentication, you should be redirected to your profile page

---

## 5. Troubleshooting

### Common Issues

#### Issue 1: Redirect URI Mismatch
**Error**: "redirect_uri_mismatch"

**Solution**: 
- Make sure the redirect URI in your OAuth app matches exactly:
  - Google: `http://localhost:8080/login/oauth2/code/google`
  - GitHub: `http://localhost:8080/login/oauth2/code/github`
- No trailing slashes
- Check http vs https

#### Issue 2: Invalid Client
**Error**: "invalid_client"

**Solution**:
- Verify your Client ID and Client Secret are correct
- Make sure there are no extra spaces in your configuration
- Regenerate the client secret if needed

#### Issue 3: Scope Issues
**Error**: User data not being retrieved

**Solution**:
- Verify the scopes in your application.properties match what you configured
- Google: `email,profile`
- GitHub: `user:email,read:user`

#### Issue 4: Session/Cookie Issues
**Solution**:
- Clear browser cookies
- Try in incognito/private mode
- Check if `server.servlet.session.cookie.secure=true` is causing issues on localhost (set to `false` for development)

---

## 6. Additional Configuration for Production

When deploying to production, update your redirect URIs:

### Google Cloud Console
Add production redirect URI:
```
https://yourdomain.com/login/oauth2/code/google
```

### GitHub OAuth App
Update Authorization callback URL:
```
https://yourdomain.com/login/oauth2/code/github
```

### Update application.properties
Consider using different profiles for different environments:
- `application-dev.properties` (localhost)
- `application-prod.properties` (production domain)

---

## 7. Verification Checklist

- [ ] Google OAuth App created
- [ ] Google Client ID and Secret obtained
- [ ] Google redirect URI configured: `http://localhost:8080/login/oauth2/code/google`
- [ ] GitHub OAuth App created
- [ ] GitHub Client ID and Secret obtained
- [ ] GitHub redirect URI configured: `http://localhost:8080/login/oauth2/code/github`
- [ ] application.properties updated with credentials
- [ ] Credentials not committed to version control
- [ ] Application runs without errors
- [ ] Can login with Google
- [ ] Can login with GitHub
- [ ] User profile displays correctly

---

## Need Help?

If you encounter any issues:
1. Check the application logs for detailed error messages
2. Verify all credentials are correct
3. Ensure redirect URIs match exactly
4. Test in incognito mode to rule out cookie/cache issues
