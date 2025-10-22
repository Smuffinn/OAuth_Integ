# PowerShell script to set OAuth2 environment variables
# Usage: .\set-env.ps1

Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "  OAuth2 Environment Setup" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""

# Function to read sensitive input
function Read-SecretInput {
    param([string]$Prompt)
    
    Write-Host $Prompt -NoNewline
    $secure = Read-Host -AsSecureString
    $bstr = [System.Runtime.InteropServices.Marshal]::SecureStringToBSTR($secure)
    $value = [System.Runtime.InteropServices.Marshal]::PtrToStringAuto($bstr)
    [System.Runtime.InteropServices.Marshal]::ZeroFreeBSTR($bstr)
    return $value
}

# Check if .env file exists
if (Test-Path ".env") {
    Write-Host "Found .env file. Loading variables..." -ForegroundColor Green
    Get-Content .env | ForEach-Object {
        if ($_ -match '^([^=]+)=(.*)$' -and -not $_.StartsWith('#')) {
            $name = $matches[1].Trim()
            $value = $matches[2].Trim()
            [Environment]::SetEnvironmentVariable($name, $value, 'Process')
            Write-Host "  ✓ Set $name" -ForegroundColor Green
        }
    }
    Write-Host ""
    Write-Host "Environment variables loaded successfully!" -ForegroundColor Green
} else {
    Write-Host ".env file not found. Please enter your OAuth2 credentials:" -ForegroundColor Yellow
    Write-Host ""
    
    # Google OAuth2
    Write-Host "Google OAuth2 Configuration:" -ForegroundColor Cyan
    $googleClientId = Read-Host "  Enter Google Client ID"
    $googleClientSecret = Read-SecretInput "  Enter Google Client Secret: "
    
    Write-Host ""
    
    # GitHub OAuth2
    Write-Host "GitHub OAuth2 Configuration:" -ForegroundColor Cyan
    $githubClientId = Read-Host "  Enter GitHub Client ID"
    $githubClientSecret = Read-SecretInput "  Enter GitHub Client Secret: "
    
    # Set environment variables
    [Environment]::SetEnvironmentVariable('GOOGLE_CLIENT_ID', $googleClientId, 'Process')
    [Environment]::SetEnvironmentVariable('GOOGLE_CLIENT_SECRET', $googleClientSecret, 'Process')
    [Environment]::SetEnvironmentVariable('GITHUB_CLIENT_ID', $githubClientId, 'Process')
    [Environment]::SetEnvironmentVariable('GITHUB_CLIENT_SECRET', $githubClientSecret, 'Process')
    
    Write-Host ""
    Write-Host "Environment variables set successfully!" -ForegroundColor Green
    
    # Ask if user wants to save to .env file
    Write-Host ""
    $save = Read-Host "Do you want to save these credentials to .env file? (y/N)"
    if ($save -eq 'y' -or $save -eq 'Y') {
        $envContent = @"
# Google OAuth2 Credentials
GOOGLE_CLIENT_ID=$googleClientId
GOOGLE_CLIENT_SECRET=$googleClientSecret

# GitHub OAuth2 Credentials
GITHUB_CLIENT_ID=$githubClientId
GITHUB_CLIENT_SECRET=$githubClientSecret
"@
        $envContent | Out-File -FilePath ".env" -Encoding UTF8
        Write-Host "✓ Credentials saved to .env file" -ForegroundColor Green
    }
}

Write-Host ""
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host "You can now run: mvn spring-boot:run" -ForegroundColor Cyan
Write-Host "=====================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Note: These environment variables are set for this PowerShell session only." -ForegroundColor Yellow
Write-Host "You'll need to run this script each time you open a new terminal." -ForegroundColor Yellow
