#!/bin/bash
mkdir -p components/shared-components
cp -r ../share/components/* components/shared-components
find components/shared-components -name '*.vue' -exec sed -i -e 's|composition-api-source|@nuxtjs/composition-api|g' {} \;
