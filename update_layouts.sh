#!/bin/bash

# Update toolbar declarations
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v7.widget.Toolbar/androidx.appcompat.widget.Toolbar/g' {} \;

# Update other support widgets if they exist
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v4.widget/androidx.core.widget/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.design/com.google.android.material/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.constraint/androidx.constraintlayout.widget/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v7.widget.RecyclerView/androidx.recyclerview.widget.RecyclerView/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v7.widget.CardView/androidx.cardview.widget.CardView/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v7.widget.AppCompatButton/androidx.appcompat.widget.AppCompatButton/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v7.widget.AppCompatTextView/androidx.appcompat.widget.AppCompatTextView/g' {} \;
find app/src/main/res/layout -name "*.xml" -exec sed -i 's/android.support.v7.widget.AppCompatImageView/androidx.appcompat.widget.AppCompatImageView/g' {} \;

echo "Layout files updated to use AndroidX components" 