# deploy.ps1

$REPOSITORY = "C:\BE05-FIN-2TEAM-MINGLECRM-BACKEND"
cd $REPOSITORY

$APP_NAME = "cicd-test"
$JAR_NAME = Get-ChildItem -Path "$REPOSITORY\build\libs" -Filter '*.jar' | Sort-Object LastWriteTime -Descending | Select-Object -First 1
$JAR_PATH = "$REPOSITORY\build\libs\$JAR_NAME"

$CURRENT_PID = Get-Process | Where-Object { $_.Path -match $APP_NAME }

if ($CURRENT_PID -eq $null) {
    Write-Host "> 종료할 것 없음."
} else {
    Write-Host "> kill -15 $($CURRENT_PID.Id)"
    Stop-Process -Id $CURRENT_PID.Id -Force
    Start-Sleep -Seconds 5
}

Write-Host "> $JAR_PATH 배포"
Start-Process -FilePath "java" -ArgumentList "-jar", "$JAR_PATH" -NoNewWindow -RedirectStandardOutput $null -RedirectStandardError $null
