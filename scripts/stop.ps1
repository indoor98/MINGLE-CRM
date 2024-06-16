# stop.ps1

$REPOSITORY = "C:\BE05-FIN-2TEAM-MINGLECRM-BACKEND"
cd $REPOSITORY

$APP_NAME = "cicd-test"
$CURRENT_PID = Get-Process | Where-Object { $_.Path -match $APP_NAME }

if ($CURRENT_PID -eq $null) {
  Write-Host "> 종료할 것 없음."
} else {
  Write-Host "> kill -15 $($CURRENT_PID.Id)"
  Stop-Process -Id $CURRENT_PID.Id -Force
  Start-Sleep -Seconds 5
}
