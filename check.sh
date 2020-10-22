#! /bin/bash

gradle -q jar
pth_tst=""
pth_ans=""
num_of_tests=11
for ((tst = 1; tst <= num_of_tests; tst++))
do
    if (("$tst" < 10))
    then
      pth_tst="data/integTests/0$tst.in"
      pth_ans="data/integTests/0$tst.out"
    else
      pth_tst="data/integTests/$tst.in"
      pth_ans="data/integTests/$tst.out"
    fi

    java -jar build/libs/visitor.jar $pth_tst > cur.txt
    diff "cur.txt" "$pth_ans" > diff.txt
    if [ -s diff.txt ]
      then
        echo "$tst test: FAIL"
        exit 202
      else
        echo "$tst test: OK"
    fi
done

rm -f diff.txt cur.txt
gradle clean

echo "tests passed"