#!/bin/bash
mkdir -p src/shared-components
cp -r ../share/components/* src/shared-components
find src/shared-components -name '*.vue' -exec sed -i -e 's|composition-api-source|vue|g' {} \;
