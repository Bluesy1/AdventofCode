set unstable

_default:
  just --list

# error out if this isn't being run in a venv
_require-venv:
    #!/usr/bin/env python
    import sys
    sys.exit(sys.prefix == sys.base_prefix)

# install dev deps
@install: _require-venv
  # extra flags make this ~ as fast as I want
  pip install -r requirements.txt --quiet --disable-pip-version-check

# run linting and typecheking over the solutions
@lint: _require-venv install
  ruff check --quiet
  ruff format --check --quiet
  pyright

# run every solution for a given year
[script("bash")]
@validate year:
  if [ "{{year}}" -ge 2025 ]; then \ 
    for i in $(seq 1 12); do ./advent $i --slow --year {{year}}; echo; done; \
  else \
    for i in $(seq 1 25); do ./advent $i --slow --year {{year}}; echo; done; \
  fi
