#!/bin/sh
mkdir -p components/shared-components
cp -r ../share/components/* components/shared-components
find components/shared-components -name '*.vue' -exec sed -i -e 's|composition-api-source|@nuxtjs/composition-api|g' {} \;

mkdir -p shared-modules/models
cp -r ../share/javascript/models/* shared-modules/models

mkdir -p shared-modules/services
cp -r ../share/javascript/services/* shared-modules/services

mkdir -p shared-modules/helpers
cp -r ../share/javascript/helpers/* shared-modules/helpers

mkdir -p shared-modules/DateTime
cp -r ../share/javascript/DateTime/* shared-modules/DateTime
